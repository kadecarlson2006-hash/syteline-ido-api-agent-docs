package com.operator.app.ui

import com.operator.app.audio.AudioRoutes
import com.operator.app.bluetooth.BluetoothStatus
import com.operator.core.audio.AudioLoopbackState
import com.operator.core.config.OperatorConfig
import com.operator.core.diagnostics.RouteEvent
import com.operator.core.glasses.GlassesAction
import com.operator.core.glasses.GlassesState
import com.operator.core.model.OperatorMode
import com.operator.core.model.OperatorState
import com.operator.core.model.WitLevel

/** Everything the main screen renders, as one immutable value. */
data class OperatorUiState(
    val operator: OperatorState = OperatorState(OperatorMode.STANDBY, WitLevel.NORMAL),
    val loopback: AudioLoopbackState = AudioLoopbackState(),
    val routes: AudioRoutes = AudioRoutes(),
    val microphonePermissionGranted: Boolean = false,
    val bluetooth: BluetoothStatus = BluetoothStatus(),
    val bluetoothPermissionGranted: Boolean = false,
    val bluetoothPermissionIsRuntime: Boolean = false,
    val routeEvents: List<RouteEvent> = emptyList(),
    val glasses: GlassesState = GlassesState(providerName = "none", sdkPresent = false),
    val glassesActions: List<GlassesAction> = emptyList(),
    /** Short description of the last state event, for the diagnostics card. */
    val lastEvent: String? = null,
    val config: OperatorConfig = OperatorConfig(),
    val appVersion: String = "",
)
