package com.operator.core.diagnostics

import com.operator.core.audio.AudioRoute
import com.operator.core.audio.RecordingState

/**
 * Everything the developer diagnostics panel shows, as one immutable snapshot.
 * Fields for future milestones are present but null so the panel can render
 * "—" rather than every milestone re-shaping the type.
 */
data class DiagnosticsSnapshot(
    // Milestone 1: audio
    val microphonePermissionGranted: Boolean = false,
    val recordingState: RecordingState = RecordingState.IDLE,
    val availableInputs: List<AudioRoute> = emptyList(),
    val availableOutputs: List<AudioRoute> = emptyList(),
    /** Device Android *actually* routed the last recording to (AudioRecord.routedDevice). */
    val actualInputRoute: AudioRoute? = null,
    /** Device Android *actually* routed the last playback to (AudioTrack.routedDevice). */
    val actualOutputRoute: AudioRoute? = null,
    val lastClipDurationMillis: Long? = null,
    val lastClipPeakLevel: Float? = null,
    val lastAudioError: String? = null,

    // Later milestones (placeholders)
    val aiProvider: String? = null,
    val aiModel: String? = null,
    val decisionModel: String? = null,
    val ttsProvider: String? = null,
    val voiceId: String? = null,
    val transcriptionProvider: String? = null,
    val glassesState: String? = null,
    val remoteControllerState: String? = null,
    val latency: LatencyTimeline = LatencyTimeline(),
    val promptVersion: String? = null,
)
