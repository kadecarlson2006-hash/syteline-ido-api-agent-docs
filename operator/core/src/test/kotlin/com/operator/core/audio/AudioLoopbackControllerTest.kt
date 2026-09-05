package com.operator.core.audio

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Note: the controller is given the TestScope itself (not `backgroundScope`) because
 * `advanceUntilIdle()` only drives *foreground* tasks; background-scope coroutines would sit
 * on their delays forever. All launched jobs complete or are cancelled within each test.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class AudioLoopbackControllerTest {

    private val mic = AudioRoute(1, "Built-in mic", AudioRouteKind.BUILTIN_MIC, isSource = true, isSink = false)
    private val speaker = AudioRoute(2, "Speaker", AudioRouteKind.BUILTIN_SPEAKER, isSource = false, isSink = true)
    private val scoMic = AudioRoute(7, "Glasses", AudioRouteKind.BLUETOOTH_SCO, isSource = true, isSink = false, address = "AA:BB")
    private val scoOut = AudioRoute(8, "Glasses", AudioRouteKind.BLUETOOTH_SCO, isSource = false, isSink = true, address = "AA:BB")

    private class FakeRecorder(
        private val route: AudioRoute?,
        private val takesMillis: Long = 1_000,
        private val failure: Exception? = null,
        private val amplitude: Short = 8_000,
    ) : AudioRecorder {
        var calls = 0
        var lastSelection: RouteSelection? = null
        override suspend fun record(durationMillis: Long, selection: RouteSelection, onProgress: (Long) -> Unit): RecordingResult {
            calls++
            lastSelection = selection
            delay(takesMillis / 2)
            onProgress(durationMillis / 2)
            delay(takesMillis / 2)
            failure?.let { throw it }
            val frames = (16_000 * durationMillis / 1000).toInt()
            return RecordingResult(PcmClip(ShortArray(frames) { amplitude }, 16_000, 1), route, note = "source=${selection.describeInput}")
        }
    }

    private class FakePlayer(private val route: AudioRoute?, private val failure: Exception? = null) : AudioPlayer {
        var played: PcmClip? = null
        var lastSelection: RouteSelection? = null
        override suspend fun play(clip: PcmClip, selection: RouteSelection, onProgress: (Long) -> Unit): PlaybackResult {
            played = clip
            lastSelection = selection
            delay(clip.durationMillis)
            failure?.let { throw it }
            onProgress(clip.durationMillis)
            return PlaybackResult(route, clip.durationMillis, note = "sink=${selection.describeOutput}")
        }
    }

    @Test
    fun `record then play happy path`() = runTest {
        val recorder = FakeRecorder(mic)
        val player = FakePlayer(speaker)
        val c = AudioLoopbackController(recorder, player, this, recordDurationMillis = 3_000)

        assertEquals(RecordingState.IDLE, c.state.value.recordingState)
        assertFalse(c.startPlayTest(), "nothing to play yet")

        assertTrue(c.startRecordTest())
        assertEquals(RecordingState.RECORDING, c.state.value.recordingState)
        assertFalse(c.startRecordTest(), "busy: second record must be rejected")
        assertFalse(c.startPlayTest(), "busy: play must be rejected while recording")

        advanceTimeBy(600)
        assertEquals(1_500, c.state.value.progressMillis)
        advanceUntilIdle()

        val recorded = c.state.value
        assertEquals(RecordingState.RECORDED, recorded.recordingState)
        assertTrue(recorded.hasClip)
        assertEquals(3_000, recorded.clipDurationMillis)
        assertEquals(mic, recorded.lastInputRoute)
        assertEquals("source=system default", recorded.lastCaptureNote)
        assertNotNull(recorded.clipPeakLevel)
        assertTrue(recorded.clipPeakLevel > 0.2f)
        assertNull(recorded.error)

        assertTrue(c.startPlayTest())
        assertEquals(RecordingState.PLAYING, c.state.value.recordingState)
        advanceUntilIdle()
        val played = c.state.value
        assertEquals(RecordingState.RECORDED, played.recordingState)
        assertEquals(speaker, played.lastOutputRoute)
        assertEquals("sink=system default", played.lastPlaybackNote)
        assertEquals(3_000, player.played?.durationMillis)
        assertEquals(1, recorder.calls)
    }

    @Test
    fun `route selection is passed to recorder and player`() = runTest {
        val recorder = FakeRecorder(scoMic)
        val player = FakePlayer(scoOut)
        val c = AudioLoopbackController(recorder, player, this, 1_000)
        c.selectInput(scoMic)
        c.selectOutput(scoOut)
        assertTrue(c.state.value.selection.needsCommunicationLink)

        c.startRecordTest(); advanceUntilIdle()
        assertEquals(scoMic, recorder.lastSelection?.input)
        c.startPlayTest(); advanceUntilIdle()
        assertEquals(scoOut, player.lastSelection?.output)

        c.selectInput(null)
        assertNull(c.state.value.selection.input)
        assertEquals(scoOut, c.state.value.selection.output, "clearing input keeps output")
    }

    @Test
    fun `a selected device that disappears falls back to default`() = runTest {
        val c = AudioLoopbackController(FakeRecorder(mic), FakePlayer(speaker), this, 1_000)
        c.selectInput(scoMic)
        c.selectOutput(scoOut)
        c.onRoutesChanged(inputs = listOf(mic, scoMic), outputs = listOf(speaker)) // SCO output gone
        assertEquals(scoMic, c.state.value.selection.input)
        assertNull(c.state.value.selection.output)
        c.onRoutesChanged(inputs = listOf(mic), outputs = listOf(speaker))
        assertEquals(RouteSelection.Default, c.state.value.selection)
    }

    @Test
    fun `recording failure lands in ERROR and can be retried`() = runTest {
        val c = AudioLoopbackController(
            FakeRecorder(mic, failure = AudioException("mic busy")),
            FakePlayer(speaker),
            this,
            recordDurationMillis = 2_000,
        )
        assertTrue(c.startRecordTest())
        advanceUntilIdle()
        assertEquals(RecordingState.ERROR, c.state.value.recordingState)
        assertEquals("Recording failed: mic busy", c.state.value.error)
        assertFalse(c.state.value.hasClip)
        assertTrue(c.startRecordTest(), "ERROR is not busy; retry allowed")
    }

    @Test
    fun `playback failure keeps the clip`() = runTest {
        val c = AudioLoopbackController(
            FakeRecorder(mic),
            FakePlayer(speaker, failure = AudioException("no output")),
            this,
            recordDurationMillis = 2_000,
        )
        c.startRecordTest(); advanceUntilIdle()
        c.startPlayTest(); advanceUntilIdle()
        assertEquals(RecordingState.ERROR, c.state.value.recordingState)
        assertTrue(c.state.value.hasClip)
        assertTrue(c.startPlayTest(), "clip survives a playback failure")
    }

    @Test
    fun `cancel during recording returns to IDLE and during playback to RECORDED`() = runTest {
        val c = AudioLoopbackController(FakeRecorder(mic), FakePlayer(speaker), this, 2_000)
        c.startRecordTest()
        advanceTimeBy(100)
        c.cancel()
        advanceUntilIdle()
        assertEquals(RecordingState.IDLE, c.state.value.recordingState)
        assertFalse(c.state.value.hasClip)

        c.startRecordTest(); advanceUntilIdle()
        c.startPlayTest()
        advanceTimeBy(100)
        c.cancel()
        advanceUntilIdle()
        assertEquals(RecordingState.RECORDED, c.state.value.recordingState)
        assertTrue(c.state.value.hasClip)
    }

    @Test
    fun `discard clip clears the clip but keeps the route selection`() = runTest {
        val c = AudioLoopbackController(FakeRecorder(mic), FakePlayer(speaker), this, 2_000)
        c.selectOutput(speaker)
        c.startRecordTest(); advanceUntilIdle()
        c.discardClip()
        val s = c.state.value
        assertEquals(RecordingState.IDLE, s.recordingState)
        assertFalse(s.hasClip)
        assertNull(s.clipDurationMillis)
        assertEquals(speaker, s.selection.output)
        assertFalse(c.startPlayTest())
    }

    @Test
    fun `pcm clip reports duration and peak`() {
        val clip = PcmClip(ShortArray(16_000) { if (it == 5) -16_384 else 0 }, 16_000, 1)
        assertEquals(1_000, clip.durationMillis)
        assertEquals(0.5f, clip.peakLevel())
    }
}
