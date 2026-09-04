package com.operator.core.diagnostics

/**
 * Named checkpoints along one spoken interaction. Latency is a critical product metric, so
 * the timeline is a first-class type from Milestone 0 even though only audio checkpoints are
 * populated until the AI/TTS milestones.
 */
enum class LatencyCheckpoint {
    SPEECH_STARTED,
    SPEECH_ENDED,
    SEGMENT_FINALIZED,
    TRANSCRIPTION_COMPLETED,
    DECISION_STARTED,
    DECISION_COMPLETED,
    AI_REQUEST_STARTED,
    AI_FIRST_TOKEN,
    TTS_REQUEST_STARTED,
    TTS_FIRST_AUDIO,
    AUDIO_PLAYBACK_STARTED,
    AUDIO_PLAYBACK_COMPLETED,
}

/**
 * Immutable record of checkpoint timestamps (milliseconds on a monotonic clock).
 * Add checkpoints with [mark]; derive intervals with [between].
 */
class LatencyTimeline private constructor(
    private val marks: Map<LatencyCheckpoint, Long>,
) {
    constructor() : this(emptyMap())

    fun mark(checkpoint: LatencyCheckpoint, atMillis: Long): LatencyTimeline =
        LatencyTimeline(marks + (checkpoint to atMillis))

    fun at(checkpoint: LatencyCheckpoint): Long? = marks[checkpoint]

    /** Milliseconds from [from] to [to], or null if either is missing. */
    fun between(from: LatencyCheckpoint, to: LatencyCheckpoint): Long? {
        val a = marks[from] ?: return null
        val b = marks[to] ?: return null
        return b - a
    }

    val transcriptionLatency get() = between(LatencyCheckpoint.SPEECH_ENDED, LatencyCheckpoint.TRANSCRIPTION_COMPLETED)
    val decisionLatency get() = between(LatencyCheckpoint.DECISION_STARTED, LatencyCheckpoint.DECISION_COMPLETED)
    val modelLatency get() = between(LatencyCheckpoint.AI_REQUEST_STARTED, LatencyCheckpoint.AI_FIRST_TOKEN)
    val ttsLatency get() = between(LatencyCheckpoint.TTS_REQUEST_STARTED, LatencyCheckpoint.TTS_FIRST_AUDIO)
    val audioLatency get() = between(LatencyCheckpoint.TTS_FIRST_AUDIO, LatencyCheckpoint.AUDIO_PLAYBACK_STARTED)
    val totalPerceivedLatency get() = between(LatencyCheckpoint.SPEECH_ENDED, LatencyCheckpoint.AUDIO_PLAYBACK_STARTED)

    fun isEmpty() = marks.isEmpty()
}
