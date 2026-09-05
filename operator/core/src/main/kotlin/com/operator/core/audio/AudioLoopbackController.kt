package com.operator.core.audio

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** Observable state of the loopback test. The PCM clip itself is not exposed. */
data class AudioLoopbackState(
    val recordingState: RecordingState = RecordingState.IDLE,
    val progressMillis: Long = 0,
    val targetMillis: Long = 0,
    val hasClip: Boolean = false,
    val clipDurationMillis: Long? = null,
    val clipPeakLevel: Float? = null,
    /** Milestone 2: what the user asked for. */
    val selection: RouteSelection = RouteSelection.Default,
    /** What the platform actually did on the last run. */
    val lastInputRoute: AudioRoute? = null,
    val lastOutputRoute: AudioRoute? = null,
    val lastCaptureNote: String? = null,
    val lastPlaybackNote: String? = null,
    val error: String? = null,
)

/**
 * Orchestrates RECORD TEST → (clip in memory) → PLAY TEST, with an explicit route selection.
 *
 * Platform-neutral: depends only on [AudioRecorder] / [AudioPlayer] ports, so the state
 * machine is unit-tested with fakes. The recorded clip lives only in this object's memory
 * and is discarded on [discardClip] or process death (privacy rule: no raw audio on disk).
 */
class AudioLoopbackController(
    private val recorder: AudioRecorder,
    private val player: AudioPlayer,
    private val scope: CoroutineScope,
    private val recordDurationMillis: Long,
) {
    private val _state = MutableStateFlow(AudioLoopbackState(targetMillis = recordDurationMillis))
    val state: StateFlow<AudioLoopbackState> = _state.asStateFlow()

    @Volatile private var clip: PcmClip? = null
    @Volatile private var job: Job? = null

    /** Chooses the capture device for subsequent RECORD TESTs; null restores system default. */
    fun selectInput(route: AudioRoute?) = _state.update { it.copy(selection = it.selection.copy(input = route)) }

    /** Chooses the playback device for subsequent PLAY TESTs; null restores system default. */
    fun selectOutput(route: AudioRoute?) = _state.update { it.copy(selection = it.selection.copy(output = route)) }

    /** Call whenever the platform's device list changes so a vanished selection falls back to default. */
    fun onRoutesChanged(inputs: List<AudioRoute>, outputs: List<AudioRoute>) = _state.update { s ->
        val pruned = s.selection.pruned(inputs, outputs)
        if (pruned == s.selection) s else s.copy(selection = pruned)
    }

    /** Starts RECORD TEST. Returns false (and does nothing) if a test is already running. */
    fun startRecordTest(): Boolean {
        if (!claim(RecordingState.RECORDING, recordDurationMillis)) return false
        val selection = _state.value.selection
        job = scope.launch {
            try {
                val result = recorder.record(recordDurationMillis, selection) { ms -> progress(ms) }
                clip = result.clip
                _state.update {
                    it.copy(
                        recordingState = RecordingState.RECORDED,
                        progressMillis = recordDurationMillis,
                        hasClip = true,
                        clipDurationMillis = result.clip.durationMillis,
                        clipPeakLevel = result.clip.peakLevel(),
                        lastInputRoute = result.route,
                        lastCaptureNote = result.note,
                        error = null,
                    )
                }
            } catch (e: CancellationException) {
                settle()
                throw e
            } catch (e: Exception) {
                fail("Recording failed: ${e.message ?: e::class.simpleName}")
            }
        }
        return true
    }

    /** Starts PLAY TEST of the last recorded clip. Returns false if busy or nothing is recorded. */
    fun startPlayTest(): Boolean {
        val c = clip ?: return false
        if (!claim(RecordingState.PLAYING, c.durationMillis)) return false
        val selection = _state.value.selection
        job = scope.launch {
            try {
                val result = player.play(c, selection) { ms -> progress(ms) }
                _state.update {
                    it.copy(
                        recordingState = RecordingState.RECORDED,
                        progressMillis = result.playedMillis,
                        lastOutputRoute = result.route,
                        lastPlaybackNote = result.note,
                        error = null,
                    )
                }
            } catch (e: CancellationException) {
                settle()
                throw e
            } catch (e: Exception) {
                fail("Playback failed: ${e.message ?: e::class.simpleName}")
            }
        }
        return true
    }

    /** Stops whatever is running. Safe to call when idle. */
    fun cancel() {
        job?.cancel()
        job = null
    }

    /** Drops the in-memory clip. Route selection is kept. */
    fun discardClip() {
        cancel()
        clip = null
        _state.update {
            it.copy(
                recordingState = RecordingState.IDLE,
                progressMillis = 0,
                hasClip = false,
                clipDurationMillis = null,
                clipPeakLevel = null,
                error = null,
            )
        }
    }

    private fun claim(target: RecordingState, targetMillis: Long): Boolean {
        var claimed = false
        _state.update { s ->
            claimed = !s.recordingState.isBusy
            if (!claimed) s else s.copy(recordingState = target, progressMillis = 0, targetMillis = targetMillis, error = null)
        }
        return claimed
    }

    private fun progress(ms: Long) = _state.update { it.copy(progressMillis = ms) }

    private fun settle() = _state.update {
        it.copy(recordingState = if (clip != null) RecordingState.RECORDED else RecordingState.IDLE)
    }

    private fun fail(message: String) = _state.update {
        it.copy(recordingState = RecordingState.ERROR, error = message)
    }
}
