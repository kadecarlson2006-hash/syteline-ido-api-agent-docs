package com.operator.app.audio

import android.content.Context
import android.media.AudioDeviceCallback
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.os.Handler
import android.os.Looper
import com.operator.core.audio.AudioRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/** Snapshot of what Android currently offers as capture sources and playback sinks. */
data class AudioRoutes(
    val inputs: List<AudioRoute> = emptyList(),
    val outputs: List<AudioRoute> = emptyList(),
)

/**
 * Observes [AudioManager] device add/remove events and exposes the current inputs/outputs.
 * This shows what is *available*; the device Android *actually* uses for a given stream is
 * reported by the recorder/player via `routedDevice` (see [AndroidAudioRecorder]).
 */
class AudioRouteMonitor(context: Context) {
    private val audioManager = context.getSystemService(AudioManager::class.java)
    private val _routes = MutableStateFlow(AudioRoutes())
    val routes: StateFlow<AudioRoutes> = _routes.asStateFlow()

    private val callback = object : AudioDeviceCallback() {
        override fun onAudioDevicesAdded(addedDevices: Array<out AudioDeviceInfo>) = refresh()
        override fun onAudioDevicesRemoved(removedDevices: Array<out AudioDeviceInfo>) = refresh()
    }

    private var started = false

    fun start() {
        if (started) return
        started = true
        refresh()
        audioManager.registerAudioDeviceCallback(callback, Handler(Looper.getMainLooper()))
    }

    fun stop() {
        if (!started) return
        started = false
        audioManager.unregisterAudioDeviceCallback(callback)
    }

    fun refresh() {
        val inputs = audioManager.getDevices(AudioManager.GET_DEVICES_INPUTS).map(AudioRouteMapper::toRoute)
        val outputs = audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS).map(AudioRouteMapper::toRoute)
        _routes.value = AudioRoutes(inputs = inputs, outputs = outputs)
    }
}
