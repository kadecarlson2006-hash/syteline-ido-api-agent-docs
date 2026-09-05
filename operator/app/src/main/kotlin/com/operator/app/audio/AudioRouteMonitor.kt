package com.operator.app.audio

import android.content.Context
import android.media.AudioDeviceCallback
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import com.operator.core.audio.AudioRoute
import com.operator.core.diagnostics.RouteEventLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/** Snapshot of what Android currently offers as capture sources and playback sinks. */
data class AudioRoutes(
    val inputs: List<AudioRoute> = emptyList(),
    val outputs: List<AudioRoute> = emptyList(),
    /** Sinks eligible for `AudioManager.setCommunicationDevice` (API 31+). Empty below API 31. */
    val communicationDevices: List<AudioRoute> = emptyList(),
    /** The communication device currently selected, if any (API 31+). */
    val activeCommunicationDevice: AudioRoute? = null,
    /** `AudioManager.getMode()` as a label (NORMAL, IN_COMMUNICATION, …). */
    val audioMode: String = "NORMAL",
    val supportsCommunicationDeviceApi: Boolean = false,
) {
    val bluetoothInputs: List<AudioRoute> get() = inputs.filter { it.isBluetooth }
    val bluetoothOutputs: List<AudioRoute> get() = outputs.filter { it.isBluetooth }
}

/**
 * Observes [AudioManager] device add/remove events and communication-device changes and
 * exposes the current picture. Every change is also written to the [RouteEventLog] and logcat.
 * This shows what is *available*; the device Android *actually* uses for a stream is
 * reported by the recorder/player via `routedDevice`.
 */
class AudioRouteMonitor(
    private val context: Context,
    private val eventLog: RouteEventLog,
) {
    private val audioManager = context.getSystemService(AudioManager::class.java)
    private val _routes = MutableStateFlow(AudioRoutes())
    val routes: StateFlow<AudioRoutes> = _routes.asStateFlow()

    private val deviceCallback = object : AudioDeviceCallback() {
        override fun onAudioDevicesAdded(addedDevices: Array<out AudioDeviceInfo>) {
            addedDevices.forEach { log("Device added: ${AudioRouteMapper.toRoute(it).summary}") }
            refresh()
        }

        override fun onAudioDevicesRemoved(removedDevices: Array<out AudioDeviceInfo>) {
            removedDevices.forEach { log("Device removed: ${AudioRouteMapper.toRoute(it).summary}") }
            refresh()
        }
    }

    private val communicationListener: AudioManager.OnCommunicationDeviceChangedListener? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            AudioManager.OnCommunicationDeviceChangedListener { device ->
                log("Communication device → ${device?.let { AudioRouteMapper.toRoute(it).summary } ?: "none"}")
                refresh()
            }
        } else null

    private var started = false

    fun start() {
        if (started) return
        started = true
        refresh()
        audioManager.registerAudioDeviceCallback(deviceCallback, Handler(Looper.getMainLooper()))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && communicationListener != null) {
            audioManager.addOnCommunicationDeviceChangedListener(ContextCompat.getMainExecutor(context), communicationListener)
        }
        log("Route monitor started (API ${Build.VERSION.SDK_INT})")
    }

    fun stop() {
        if (!started) return
        started = false
        audioManager.unregisterAudioDeviceCallback(deviceCallback)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && communicationListener != null) {
            audioManager.removeOnCommunicationDeviceChangedListener(communicationListener)
        }
    }

    fun refresh() {
        val inputs = audioManager.getDevices(AudioManager.GET_DEVICES_INPUTS).map(AudioRouteMapper::toRoute)
        val outputs = audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS).map(AudioRouteMapper::toRoute)
        val supportsComm = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
        val commDevices = if (supportsComm) audioManager.availableCommunicationDevices.map(AudioRouteMapper::toRoute) else emptyList()
        val active = if (supportsComm) audioManager.communicationDevice?.let(AudioRouteMapper::toRoute) else null
        _routes.value = AudioRoutes(
            inputs = inputs,
            outputs = outputs,
            communicationDevices = commDevices,
            activeCommunicationDevice = active,
            audioMode = modeLabel(audioManager.mode),
            supportsCommunicationDeviceApi = supportsComm,
        )
    }

    /** Resolves a route back to the live platform object, or null if it has gone away. */
    fun findInput(route: AudioRoute): AudioDeviceInfo? =
        audioManager.getDevices(AudioManager.GET_DEVICES_INPUTS).firstOrNull { it.id == route.id }

    fun findOutput(route: AudioRoute): AudioDeviceInfo? =
        audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS).firstOrNull { it.id == route.id }

    fun log(message: String) {
        Log.i(TAG, message)
        eventLog.log(message)
    }

    private fun modeLabel(mode: Int): String = when (mode) {
        AudioManager.MODE_NORMAL -> "NORMAL"
        AudioManager.MODE_RINGTONE -> "RINGTONE"
        AudioManager.MODE_IN_CALL -> "IN_CALL"
        AudioManager.MODE_IN_COMMUNICATION -> "IN_COMMUNICATION"
        AudioManager.MODE_CALL_SCREENING -> "CALL_SCREENING"
        else -> "MODE_$mode"
    }

    private companion object {
        const val TAG = "AudioRouteMonitor"
    }
}
