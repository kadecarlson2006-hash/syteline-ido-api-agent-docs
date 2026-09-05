package com.operator.app.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioDeviceInfo
import android.media.AudioFocusRequest
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.util.Log
import com.operator.core.audio.AudioException
import com.operator.core.audio.AudioPlayer
import com.operator.core.audio.AudioRoute
import com.operator.core.audio.PcmClip
import com.operator.core.audio.PlaybackResult
import com.operator.core.audio.RouteSelection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import kotlin.math.max
import kotlin.math.min

/**
 * Plays an in-memory PCM clip with [AudioTrack], taking transient audio focus for the duration.
 *
 * Routing policy (Milestone 2):
 *  - no selection / A2DP / wired / USB → `USAGE_MEDIA` (+ `setPreferredDevice` when selected)
 *  - Bluetooth SCO / LE headset        → communication link via [CommunicationLink]
 *                                         + `USAGE_VOICE_COMMUNICATION` + `setPreferredDevice`
 * Cancellation stops playback immediately.
 */
class AndroidAudioPlayer(
    context: Context,
    private val monitor: AudioRouteMonitor,
    private val link: CommunicationLink,
) : AudioPlayer {
    private val audioManager = context.getSystemService(AudioManager::class.java)

    override suspend fun play(clip: PcmClip, selection: RouteSelection, onProgress: (Long) -> Unit): PlaybackResult =
        withContext(Dispatchers.IO) {
            require(clip.channels == 1) { "Milestone 1 player supports mono only" }
            val output = selection.output
            when {
                output == null -> render(clip, mediaAttributes(), null, "USAGE_MEDIA, system default routing", onProgress)
                output.usesCommunicationLink -> link.use(output) { confirmed ->
                    render(
                        clip,
                        voiceAttributes(),
                        output,
                        "USAGE_VOICE_COMMUNICATION via ${output.summary}" + if (confirmed) "" else " (link not confirmed)",
                        onProgress,
                    )
                }
                else -> render(clip, mediaAttributes(), output, "USAGE_MEDIA, preferred ${output.summary}", onProgress)
            }
        }

    private fun mediaAttributes() = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_MEDIA)
        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
        .build()

    private fun voiceAttributes() = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION)
        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
        .build()

    private suspend fun render(
        clip: PcmClip,
        attributes: AudioAttributes,
        preferred: AudioRoute?,
        baseNote: String,
        onProgress: (Long) -> Unit,
    ): PlaybackResult = withContext(Dispatchers.IO) {
        val minBuffer = AudioTrack.getMinBufferSize(clip.sampleRateHz, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT)
        if (minBuffer <= 0) throw AudioException("AudioTrack.getMinBufferSize failed ($minBuffer)")

        val preferredInfo: AudioDeviceInfo? = preferred?.let {
            monitor.findOutput(it) ?: throw AudioException("Selected output ${it.summary} is no longer available")
        }

        val focusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK)
            .setAudioAttributes(attributes)
            .build()
        val focus = audioManager.requestAudioFocus(focusRequest)
        if (focus != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            Log.w(TAG, "Audio focus not granted ($focus); playing anyway for the test")
        }

        val track = try {
            AudioTrack.Builder()
                .setAudioAttributes(attributes)
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(clip.sampleRateHz)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build(),
                )
                .setBufferSizeInBytes(max(minBuffer * 2, 16_384))
                .setTransferMode(AudioTrack.MODE_STREAM)
                .build()
        } catch (e: IllegalArgumentException) {
            audioManager.abandonAudioFocusRequest(focusRequest)
            throw AudioException("AudioTrack could not be created: ${e.message}", e)
        } catch (e: UnsupportedOperationException) {
            audioManager.abandonAudioFocusRequest(focusRequest)
            throw AudioException("AudioTrack could not be created: ${e.message}", e)
        }

        var note = baseNote
        var routed: AudioRoute? = null
        val startedAt = System.nanoTime()
        try {
            if (track.state != AudioTrack.STATE_INITIALIZED) throw AudioException("AudioTrack failed to initialize")
            if (preferredInfo != null && !track.setPreferredDevice(preferredInfo)) {
                note += " (setPreferredDevice rejected)"
            }
            track.play()

            var offset = 0
            val total = clip.samples.size
            while (offset < total) {
                ensureActive()
                val toWrite = min(AudioFormatSpec.CHUNK_FRAMES, total - offset)
                val written = track.write(clip.samples, offset, toWrite, AudioTrack.WRITE_BLOCKING)
                if (written < 0) throw AudioException("AudioTrack.write error $written")
                offset += written
                if (routed == null) {
                    routed = track.routedDevice?.let(AudioRouteMapper::toRoute)
                    if (routed != null) monitor.log("Playback routed to ${routed.summary} [$note]")
                }
                onProgress(track.playbackHeadPosition * 1000L / clip.sampleRateHz)
            }

            // Streaming writes return once buffered, not once heard: wait for the head to reach the end.
            val deadline = System.nanoTime() + (clip.durationMillis + 1_500) * 1_000_000
            while (track.playbackHeadPosition < clip.frameCount && System.nanoTime() < deadline) {
                ensureActive()
                onProgress(track.playbackHeadPosition * 1000L / clip.sampleRateHz)
                delay(20)
            }
            if (routed == null) {
                routed = track.routedDevice?.let(AudioRouteMapper::toRoute)
                monitor.log(routed?.let { "Playback routed to ${it.summary} [$note]" } ?: "Playback finished; routedDevice was never reported [$note]")
            }
        } finally {
            runCatching { track.pause(); track.flush(); track.stop() }.onFailure { Log.w(TAG, "stop failed", it) }
            track.release()
            audioManager.abandonAudioFocusRequest(focusRequest)
        }
        val playedMillis = (System.nanoTime() - startedAt) / 1_000_000
        Log.i(TAG, "Played ${clip.durationMillis}ms clip via ${routed?.summary ?: "unknown route"} in ${playedMillis}ms ($note)")
        PlaybackResult(routed, playedMillis, note)
    }

    private companion object {
        const val TAG = "AndroidAudioPlayer"
    }
}
