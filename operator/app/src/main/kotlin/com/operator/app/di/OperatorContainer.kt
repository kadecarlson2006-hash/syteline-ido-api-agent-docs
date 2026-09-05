package com.operator.app.di

import android.app.Application
import com.operator.app.audio.AndroidAudioPlayer
import com.operator.app.audio.AndroidAudioRecorder
import com.operator.app.audio.AudioRouteMonitor
import com.operator.app.audio.AudioSubsystemReporter
import com.operator.app.audio.CommunicationLink
import com.operator.app.bluetooth.BluetoothStatusMonitor
import com.operator.app.config.BuildConfigLoader
import com.operator.app.permissions.BluetoothPermission
import com.operator.app.permissions.MicrophonePermission
import com.operator.core.audio.AudioLoopbackController
import com.operator.core.audio.AudioPlayer
import com.operator.core.audio.AudioRecorder
import com.operator.core.config.OperatorConfig
import com.operator.core.decision.ResponseDecisionEngine
import com.operator.core.decision.SilentDecisionEngine
import com.operator.core.diagnostics.RouteEventLog
import com.operator.core.state.OperatorStateManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Manual dependency container (ADR-008: no DI framework until it earns its keep).
 * One instance per process, owned by [com.operator.app.OperatorApplication].
 *
 * Everything the UI needs is reachable from here; everything here is replaceable with a
 * fake for tests. Future subsystems (AI, TTS, memory, glasses, remote) are added as
 * properties on this class behind their core interfaces.
 */
class OperatorContainer(app: Application) {

    val config: OperatorConfig = BuildConfigLoader.load()

    /** Process-wide scope for long-lived collectors. Main-immediate so state updates hit the UI promptly. */
    val appScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    val stateManager = OperatorStateManager(
        initialMode = config.defaultMode,
        initialWit = config.defaultWit,
    )

    val routeEventLog = RouteEventLog()

    val microphonePermission = MicrophonePermission(app)
    val bluetoothPermission = BluetoothPermission(app)

    val audioRouteMonitor = AudioRouteMonitor(app, routeEventLog)
    val bluetoothStatus = BluetoothStatusMonitor(app, bluetoothPermission, routeEventLog)

    private val communicationLink = CommunicationLink(app, audioRouteMonitor)
    val audioRecorder: AudioRecorder = AndroidAudioRecorder(app, audioRouteMonitor, communicationLink)
    val audioPlayer: AudioPlayer = AndroidAudioPlayer(app, audioRouteMonitor, communicationLink)

    val loopback = AudioLoopbackController(
        recorder = audioRecorder,
        player = audioPlayer,
        scope = appScope,
        recordDurationMillis = config.recordTestDurationMillis,
    )

    /** Placeholder until Milestone 12. Always NO_RESPONSE. */
    val decisionEngine: ResponseDecisionEngine = SilentDecisionEngine

    private val audioSubsystemReporter = AudioSubsystemReporter(
        stateManager = stateManager,
        permission = microphonePermission,
        routes = audioRouteMonitor,
        loopback = loopback,
    )

    init {
        audioRouteMonitor.start()
        bluetoothStatus.start()
        audioSubsystemReporter.start(appScope)
        // A selected device that disconnects must not silently keep being "selected".
        audioRouteMonitor.routes
            .onEach { loopback.onRoutesChanged(it.inputs, it.outputs) }
            .launchIn(appScope)
    }
}
