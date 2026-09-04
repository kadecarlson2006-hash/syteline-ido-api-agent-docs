package com.operator.core.diagnostics

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class LatencyTimelineTest {

    @Test
    fun `intervals are computed between marked checkpoints`() {
        val t = LatencyTimeline()
            .mark(LatencyCheckpoint.SPEECH_ENDED, 1_000)
            .mark(LatencyCheckpoint.TRANSCRIPTION_COMPLETED, 1_350)
            .mark(LatencyCheckpoint.AI_REQUEST_STARTED, 1_400)
            .mark(LatencyCheckpoint.AI_FIRST_TOKEN, 1_900)
            .mark(LatencyCheckpoint.TTS_REQUEST_STARTED, 1_950)
            .mark(LatencyCheckpoint.TTS_FIRST_AUDIO, 2_250)
            .mark(LatencyCheckpoint.AUDIO_PLAYBACK_STARTED, 2_300)
        assertEquals(350, t.transcriptionLatency)
        assertEquals(500, t.modelLatency)
        assertEquals(300, t.ttsLatency)
        assertEquals(50, t.audioLatency)
        assertEquals(1_300, t.totalPerceivedLatency)
    }

    @Test
    fun `missing checkpoints yield null rather than throwing`() {
        val t = LatencyTimeline().mark(LatencyCheckpoint.SPEECH_ENDED, 10)
        assertNull(t.transcriptionLatency)
        assertNull(t.totalPerceivedLatency)
    }

    @Test
    fun `timeline is immutable`() {
        val empty = LatencyTimeline()
        val marked = empty.mark(LatencyCheckpoint.SPEECH_STARTED, 5)
        assertTrue(empty.isEmpty())
        assertEquals(5, marked.at(LatencyCheckpoint.SPEECH_STARTED))
    }
}
