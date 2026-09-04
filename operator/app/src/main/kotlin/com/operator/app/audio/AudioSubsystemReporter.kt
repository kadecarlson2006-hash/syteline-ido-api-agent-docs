package com.operator.app.audio

import com.operator.app.permissions.MicrophonePermission
import com.operator.core.audio.AudioLoopbackController
import com.operator.core.audio.RecordingState
import com.operator.core.model.Subsystem
import com.operator.core.model.SubsystemState
import com.operator.core.model.SubsystemStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Derives the MICROPHONE and AUDIO OUTPUT subsystem statuses shown on the main screen from
 * permission state, available routes, and the loopback state machine.
 */
class AudioSubsystemReporter(
    private val stateManager: com.operator.core.state.OperatorStateManager,
    private val permission: MicrophonePermission,
    private val routes: AudioRouteMonitor,
    private val loopback: AudioLoopbackController,
) {
    fun start(scope: CoroutineScope) {
        combine(permission.granted, routes.routes, loopback.state) { granted, routes, loop ->
            val mic = when {
                !granted -> SubsystemStatus(SubsystemState.NOT_CONFIGURED, "Permission required")
                routes.inputs.isEmpty() -> SubsystemStatus(SubsystemState.UNAVAILABLE, "No input devices")
                loop.recordingState == RecordingState.RECORDING -> SubsystemStatus(SubsystemState.ACTIVE, "Recording")
                loop.recordingState == RecordingState.ERROR && loop.error?.startsWith("Recording") == true ->
                    SubsystemStatus(SubsystemState.ERROR, loop.error)
                else -> SubsystemStatus(SubsystemState.READY, loop.lastInputRoute?.summary ?: "${routes.inputs.size} input(s)")
            }
            val out = when {
                routes.outputs.isEmpty() -> SubsystemStatus(SubsystemState.UNAVAILABLE, "No output devices")
                loop.recordingState == RecordingState.PLAYING -> SubsystemStatus(SubsystemState.ACTIVE, "Playing")
                loop.recordingState == RecordingState.ERROR && loop.error?.startsWith("Playback") == true ->
                    SubsystemStatus(SubsystemState.ERROR, loop.error)
                else -> SubsystemStatus(SubsystemState.READY, loop.lastOutputRoute?.summary ?: "${routes.outputs.size} output(s)")
            }
            mic to out
        }.onEach { (mic, out) ->
            stateManager.updateSubsystem(Subsystem.MICROPHONE, mic)
            stateManager.updateSubsystem(Subsystem.AUDIO_OUTPUT, out)
        }.launchIn(scope)
    }
}
