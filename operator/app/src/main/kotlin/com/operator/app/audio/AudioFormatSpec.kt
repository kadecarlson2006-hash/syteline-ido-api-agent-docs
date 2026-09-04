package com.operator.app.audio

/** PCM format used by the Milestone 1 loopback. 16 kHz mono is the usual speech-model input. */
internal object AudioFormatSpec {
    const val SAMPLE_RATE_HZ = 16_000
    const val CHANNELS = 1
    /** Frames per read/write chunk (20 ms at 16 kHz). Small enough for responsive cancellation. */
    const val CHUNK_FRAMES = 320
}
