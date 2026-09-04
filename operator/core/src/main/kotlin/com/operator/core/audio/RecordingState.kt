package com.operator.core.audio

/** State machine for the Milestone 1 RECORD TEST / PLAY TEST loop. */
enum class RecordingState(val label: String) {
    IDLE("IDLE"),
    RECORDING("RECORDING"),
    RECORDED("RECORDED"),
    PLAYING("PLAYING"),
    ERROR("ERROR");

    val isBusy: Boolean get() = this == RECORDING || this == PLAYING
}

/** Immutable PCM clip held in memory. Never persisted to disk (privacy: ADR-003). */
class PcmClip(
    val samples: ShortArray,
    val sampleRateHz: Int,
    val channels: Int,
) {
    val frameCount: Int get() = samples.size / channels
    val durationMillis: Long get() = frameCount * 1000L / sampleRateHz

    /** Peak absolute amplitude as a fraction of full scale (0.0..1.0). Useful to prove the mic heard something. */
    fun peakLevel(): Float {
        var peak = 0
        for (s in samples) {
            val a = if (s < 0) -s.toInt() else s.toInt()
            if (a > peak) peak = a
        }
        return peak / 32768f
    }
}
