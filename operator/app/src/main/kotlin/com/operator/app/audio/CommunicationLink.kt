package com.operator.app.audio

import android.content.Context
import android.media.AudioManager
import android.os.Build
import android.os.SystemClock
import com.operator.core.audio.AudioException
import com.operator.core.audio.AudioRoute
import kotlinx.coroutines.delay

/**
 * Brings up a Bluetooth SCO / LE-audio headset link for the duration of a capture or playback
 * using the documented API 31+ path (`AudioManager.setCommunicationDevice`), which replaces the
 * deprecated `startBluetoothSco`. The request is always cleared afterwards, as the platform
 * documentation requires.
 *
 * Per the reference: only *sink* devices from `getAvailableCommunicationDevices()` may be
 * selected; the platform picks the matching source automatically.
 */
class CommunicationLink(
    context: Context,
    private val monitor: AudioRouteMonitor,
) {
    private val audioManager = context.getSystemService(AudioManager::class.java)

    /** Runs [block] with [sink] selected as the communication device. Restores the previous state afterwards. */
    suspend fun <T> use(sink: AudioRoute, block: suspend (confirmed: Boolean) -> T): T {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            throw AudioException("Selecting a Bluetooth communication device requires Android 12 (API 31) or newer")
        }
        val device = audioManager.availableCommunicationDevices.firstOrNull { it.id == sink.id }
            ?: throw AudioException("${sink.summary} is not an available communication device")

        val previousMode = audioManager.mode
        audioManager.mode = AudioManager.MODE_IN_COMMUNICATION
        if (!audioManager.setCommunicationDevice(device)) {
            audioManager.mode = previousMode
            throw AudioException("setCommunicationDevice rejected ${sink.summary}")
        }
        monitor.log("Requested communication device ${sink.summary}")
        try {
            val deadline = SystemClock.elapsedRealtime() + LINK_TIMEOUT_MILLIS
            while (audioManager.communicationDevice?.id != device.id && SystemClock.elapsedRealtime() < deadline) {
                delay(50)
            }
            val confirmed = audioManager.communicationDevice?.id == device.id
            monitor.log(
                if (confirmed) "Communication device active: ${sink.summary}"
                else "Communication device NOT confirmed after ${LINK_TIMEOUT_MILLIS}ms (active: ${audioManager.communicationDevice?.productName ?: "none"})",
            )
            return block(confirmed)
        } finally {
            audioManager.clearCommunicationDevice()
            audioManager.mode = previousMode
            monitor.log("Communication device cleared; mode restored")
            monitor.refresh()
        }
    }

    private companion object {
        /** SCO links can take a few seconds to come up (per the platform docs). */
        const val LINK_TIMEOUT_MILLIS = 4_000L
    }
}
