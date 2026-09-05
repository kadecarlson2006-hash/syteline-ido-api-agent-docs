package com.operator.app.audio

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioDeviceInfo
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import com.operator.app.permissions.MicrophonePermission
import com.operator.core.audio.AudioException
import com.operator.core.audio.AudioRecorder
import com.operator.core.audio.AudioRoute
import com.operator.core.audio.PcmClip
import com.operator.core.audio.RecordingResult
import com.operator.core.audio.RouteSelection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import kotlin.math.max
import kotlin.math.min

/**
 * Captures PCM-16 mono audio with [AudioRecord] into memory.
 *
 * Routing policy (Milestone 2):
 *  - no selection            → `AudioSource.MIC`, platform default routing
 *  - wired / USB / built-in  → `AudioSource.MIC` + `setPreferredDevice`
 *  - Bluetooth SCO / LE hs   → communication link raised via [CommunicationLink]
 *                              + `AudioSource.VOICE_COMMUNICATION` + `setPreferredDevice`
 * Whatever happens, the device Android *actually* used is read back from `routedDevice`.
 */
class AndroidAudioRecorder(
    private val context: Context,
    private val monitor: AudioRouteMonitor,
    private val link: CommunicationLink,
) : AudioRecorder {

    override suspend fun record(durationMillis: Long, selection: RouteSelection, onProgress: (Long) -> Unit): RecordingResult =
        withContext(Dispatchers.IO) {
            if (!MicrophonePermission(context).refresh()) {
                throw AudioException("Microphone permission not granted")
            }
            val input = selection.input
            when {
                input == null -> capture(durationMillis, MediaRecorder.AudioSource.MIC, null, "MIC, system default routing", onProgress)
                input.usesCommunicationLink -> {
                    val sink = findCommunicationSink(input)
                        ?: throw AudioException("No communication output matches ${input.summary}; is the headset connected?")
                    link.use(sink) { confirmed ->
                        capture(
                            durationMillis,
                            MediaRecorder.AudioSource.VOICE_COMMUNICATION,
                            input,
                            "VOICE_COMMUNICATION via ${sink.summary}" + if (confirmed) "" else " (link not confirmed)",
                            onProgress,
                        )
                    }
                }
                else -> capture(durationMillis, MediaRecorder.AudioSource.MIC, input, "MIC, preferred ${input.summary}", onProgress)
            }
        }

    /** The sink counterpart of a Bluetooth input: same address first, then same link kind. */
    private fun findCommunicationSink(input: AudioRoute): AudioRoute? {
        val candidates = monitor.routes.value.communicationDevices
        return candidates.firstOrNull { it.address != null && it.address == input.address }
            ?: candidates.firstOrNull { it.kind == input.kind }
    }

    @SuppressLint("MissingPermission") // permission is verified at runtime in record()
    private suspend fun capture(
        durationMillis: Long,
        audioSource: Int,
        preferred: AudioRoute?,
        baseNote: String,
        onProgress: (Long) -> Unit,
    ): RecordingResult = withContext(Dispatchers.IO) {
        val sampleRate = AudioFormatSpec.SAMPLE_RATE_HZ
        val minBuffer = AudioRecord.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT)
        if (minBuffer <= 0) throw AudioException("AudioRecord.getMinBufferSize failed ($minBuffer)")

        val preferredInfo: AudioDeviceInfo? = preferred?.let {
            monitor.findInput(it) ?: throw AudioException("Selected input ${it.summary} is no longer available")
        }

        val record = try {
            AudioRecord.Builder()
                .setAudioSource(audioSource)
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(sampleRate)
                        .setChannelMask(AudioFormat.CHANNEL_IN_MONO)
                        .build(),
                )
                .setBufferSizeInBytes(max(minBuffer * 2, 16_384))
                .build()
        } catch (e: SecurityException) {
            throw AudioException("Microphone permission denied by the system", e)
        } catch (e: IllegalArgumentException) {
            throw AudioException("Unsupported audio format: ${e.message}", e)
        } catch (e: UnsupportedOperationException) {
            throw AudioException("AudioRecord could not be created: ${e.message}", e)
        }

        if (record.state != AudioRecord.STATE_INITIALIZED) {
            record.release()
            throw AudioException("AudioRecord failed to initialize (state=${record.state})")
        }

        var note = baseNote
        if (preferredInfo != null && !record.setPreferredDevice(preferredInfo)) {
            note += " (setPreferredDevice rejected)"
        }

        val totalFrames = (sampleRate * durationMillis / 1000L).toInt()
        val samples = ShortArray(totalFrames)
        var routed: AudioRoute? = null
        try {
            record.startRecording()
            if (record.recordingState != AudioRecord.RECORDSTATE_RECORDING) {
                throw AudioException("Microphone is busy or unavailable (recordingState=${record.recordingState})")
            }
            var offset = 0
            var lastReported = -1L
            while (offset < totalFrames) {
                ensureActive()
                val toRead = min(AudioFormatSpec.CHUNK_FRAMES, totalFrames - offset)
                val read = record.read(samples, offset, toRead, AudioRecord.READ_BLOCKING)
                if (read < 0) throw AudioException("AudioRecord.read error $read")
                offset += read
                if (routed == null) {
                    routed = record.routedDevice?.let(AudioRouteMapper::toRoute)
                    if (routed != null) monitor.log("Capture routed to ${routed.summary} [$note]")
                }
                val elapsed = offset * 1000L / sampleRate
                if (elapsed / 100 != lastReported / 100) { // throttle to ~10 updates/s
                    lastReported = elapsed
                    onProgress(elapsed)
                }
            }
        } finally {
            runCatching { record.stop() }.onFailure { Log.w(TAG, "stop() failed", it) }
            record.release()
        }
        if (routed == null) monitor.log("Capture finished; routedDevice was never reported [$note]")
        Log.i(TAG, "Recorded $totalFrames frames @ ${sampleRate}Hz via ${routed?.summary ?: "unknown route"} ($note)")
        RecordingResult(PcmClip(samples, sampleRate, AudioFormatSpec.CHANNELS), routed, note)
    }

    private companion object {
        const val TAG = "AndroidAudioRecorder"
    }
}
