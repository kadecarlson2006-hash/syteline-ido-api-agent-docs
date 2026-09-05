package com.operator.core.audio

/**
 * Platform ports for capture and playback. The Android module implements these with
 * AudioRecord / AudioTrack; tests use in-memory fakes. Both are expected to be cancellable
 * via normal coroutine cancellation and to stop hardware promptly when cancelled.
 */
interface AudioRecorder {
    /**
     * Records for [durationMillis] from the input named in [selection] (or the platform default),
     * reporting elapsed milliseconds through [onProgress].
     */
    suspend fun record(
        durationMillis: Long,
        selection: RouteSelection,
        onProgress: (elapsedMillis: Long) -> Unit,
    ): RecordingResult
}

interface AudioPlayer {
    /** Plays [clip] to completion on the output named in [selection] (or the platform default). */
    suspend fun play(
        clip: PcmClip,
        selection: RouteSelection,
        onProgress: (elapsedMillis: Long) -> Unit,
    ): PlaybackResult
}

data class RecordingResult(
    val clip: PcmClip,
    /** Device the platform actually captured from, if it could be determined. */
    val route: AudioRoute?,
    /** Short diagnostic note: audio source used, whether the preferred device was honoured, etc. */
    val note: String? = null,
)

data class PlaybackResult(
    /** Device the platform actually rendered to, if it could be determined. */
    val route: AudioRoute?,
    val playedMillis: Long,
    val note: String? = null,
)

class AudioException(message: String, cause: Throwable? = null) : Exception(message, cause)
