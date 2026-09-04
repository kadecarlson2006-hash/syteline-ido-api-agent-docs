package com.operator.app.permissions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Observable RECORD_AUDIO permission state. The Activity requests the permission; this class
 * only reads the result so that non-UI code (subsystem reporting, recorder) can react.
 */
class MicrophonePermission(private val context: Context) {
    private val _granted = MutableStateFlow(check())
    val granted: StateFlow<Boolean> = _granted.asStateFlow()

    /** Re-reads the permission state (call on resume and after a permission result). */
    fun refresh(): Boolean = check().also { _granted.value = it }

    private fun check(): Boolean =
        ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED

    companion object {
        const val PERMISSION = Manifest.permission.RECORD_AUDIO
    }
}
