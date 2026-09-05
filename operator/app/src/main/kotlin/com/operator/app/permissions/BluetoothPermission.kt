package com.operator.app.permissions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * BLUETOOTH_CONNECT is a runtime permission from API 31. Below that the install-time
 * BLUETOOTH permission covers what Milestone 2 needs, so [granted] is simply true.
 */
class BluetoothPermission(private val context: Context) {
    private val _granted = MutableStateFlow(check())
    val granted: StateFlow<Boolean> = _granted.asStateFlow()

    /** True when the platform requires a runtime grant (API 31+). */
    val isRuntimePermission: Boolean get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    fun refresh(): Boolean = check().also { _granted.value = it }

    private fun check(): Boolean =
        if (!isRuntimePermission) true
        else ContextCompat.checkSelfPermission(context, PERMISSION) == PackageManager.PERMISSION_GRANTED

    companion object {
        const val PERMISSION = Manifest.permission.BLUETOOTH_CONNECT
    }
}
