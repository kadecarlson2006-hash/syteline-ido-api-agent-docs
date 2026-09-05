package com.operator.app.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothClass
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.core.content.ContextCompat
import com.operator.app.permissions.BluetoothPermission
import com.operator.core.diagnostics.RouteEventLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class BondedDevice(val name: String, val address: String, val isAudio: Boolean)

data class BluetoothStatus(
    val supported: Boolean = false,
    val enabled: Boolean = false,
    /** Null when BLUETOOTH_CONNECT has not been granted (API 31+). */
    val bondedDevices: List<BondedDevice>? = null,
    val lastError: String? = null,
) {
    val summary: String
        get() = when {
            !supported -> "not supported"
            !enabled -> "disabled"
            bondedDevices == null -> "enabled (permission needed for bonded list)"
            else -> "enabled · ${bondedDevices.size} bonded, ${bondedDevices.count { it.isAudio }} audio"
        }
}

/**
 * Adapter state and bonded (paired) devices — enough to confirm that the glasses or headset are
 * paired and which Bluetooth name Android knows them by. Audio routing itself is the job of
 * [com.operator.app.audio.AudioRouteMonitor]; this class never connects or streams anything.
 */
class BluetoothStatusMonitor(
    private val context: Context,
    private val permission: BluetoothPermission,
    private val eventLog: RouteEventLog,
) {
    private val adapter: BluetoothAdapter? = context.getSystemService(BluetoothManager::class.java)?.adapter
    private val _status = MutableStateFlow(BluetoothStatus())
    val status: StateFlow<BluetoothStatus> = _status.asStateFlow()

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                BluetoothAdapter.ACTION_STATE_CHANGED -> log("Bluetooth adapter state changed")
                BluetoothDevice.ACTION_ACL_CONNECTED -> log("Bluetooth ACL connected")
                BluetoothDevice.ACTION_ACL_DISCONNECTED -> log("Bluetooth ACL disconnected")
            }
            refresh()
        }
    }

    private var started = false

    fun start() {
        if (started) return
        started = true
        val filter = IntentFilter().apply {
            addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
            addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
            addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
        }
        ContextCompat.registerReceiver(context, receiver, filter, ContextCompat.RECEIVER_EXPORTED)
        refresh()
    }

    fun stop() {
        if (!started) return
        started = false
        runCatching { context.unregisterReceiver(receiver) }
    }

    @SuppressLint("MissingPermission") // guarded by BluetoothPermission.refresh() and a SecurityException catch
    fun refresh() {
        val a = adapter
        if (a == null) {
            _status.value = BluetoothStatus(supported = false)
            return
        }
        val enabled = runCatching { a.isEnabled }.getOrDefault(false)
        val granted = permission.refresh()
        val bonded: List<BondedDevice>? = if (!granted) null else try {
            a.bondedDevices.map { d ->
                BondedDevice(
                    name = d.name ?: "(no name)",
                    address = d.address ?: "?",
                    isAudio = d.bluetoothClass?.majorDeviceClass == BluetoothClass.Device.Major.AUDIO_VIDEO,
                )
            }.sortedBy { it.name }
        } catch (e: SecurityException) {
            Log.w(TAG, "bondedDevices denied", e)
            null
        }
        _status.value = BluetoothStatus(supported = true, enabled = enabled, bondedDevices = bonded)
    }

    private fun log(message: String) {
        Log.i(TAG, message)
        eventLog.log(message)
    }

    private companion object {
        const val TAG = "BluetoothStatusMonitor"
    }
}
