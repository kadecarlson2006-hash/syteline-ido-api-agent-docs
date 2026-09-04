package com.operator.app.audio

import android.annotation.SuppressLint
import android.content.Context
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import kotlin.math.max
import kotlin.math.min

/**
 * Captures PCM-16 mono audio with [AudioRecord] into memory.
 *
 * Milestone 1 uses [MediaRecorder.AudioSource.MIC] and whatever input Android picks. Later
 * milestones will add preferred-device selection (Bluetooth SCO / glasses) through the same
 * class; the port ([AudioRecorder]) does not change.
 */
class AndroidAudioRecorder(private val context: Context) : AudioRecorder {

    @SuppressLint("MissingPermission") // permission is verified at runtime via MicrophonePermission just below
    override suspend fun record(durationMillis: Long, onProgress: (Long) -> Unit): RecordingResult =
        withContext(Dispatchers.IO) {
            if (!MicrophonePermission(context).refresh()) {
                throw AudioException("Microphone permission not granted")
            }

            val sampleRate = AudioFormatSpec.SAMPLE_RATE_HZ
            val minBuffer = AudioRecord.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT)
            if (minBuffer <= 0) throw AudioException("AudioRecord.getMinBufferSize failed ($minBuffer)")

            val record = try {
                AudioRecord.Builder()
                    .setAudioSource(MediaRecorder.AudioSource.MIC)
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
            Log.i(TAG, "Recorded ${totalFrames} frames @ ${sampleRate}Hz via ${routed?.summary ?: "unknown route"}")
            RecordingResult(PcmClip(samples, sampleRate, AudioFormatSpec.CHANNELS), routed)
        }

    private companion object {
        const val TAG = "AndroidAudioRecorder"
    }
}
