package com.operator.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.operator.app.ui.components.ConsolePanel
import com.operator.app.ui.components.KeyValueRow
import com.operator.app.ui.components.SubsystemRow
import com.operator.app.ui.theme.OperatorColors
import com.operator.core.audio.AudioRoute
import com.operator.core.audio.RecordingState
import com.operator.core.model.OperatorMode
import com.operator.core.model.OperatorStatus
import com.operator.core.model.Subsystem
import com.operator.core.model.WitLevel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/** Callbacks the screen can invoke. Kept as a value so previews and tests can pass no-ops. */
data class OperatorActions(
    val onActivate: () -> Unit = {},
    val onStandby: () -> Unit = {},
    val onCommentNow: () -> Unit = {},
    val onToggleMute: () -> Unit = {},
    val onSelectMode: (OperatorMode) -> Unit = {},
    val onSelectWit: (WitLevel) -> Unit = {},
    val onRequestMicrophone: () -> Unit = {},
    val onRequestBluetooth: () -> Unit = {},
    val onSelectInput: (AudioRoute?) -> Unit = {},
    val onSelectOutput: (AudioRoute?) -> Unit = {},
    val onRecordTest: () -> Unit = {},
    val onPlayTest: () -> Unit = {},
    val onStopAudio: () -> Unit = {},
    val onDiscardClip: () -> Unit = {},
    val onRefresh: () -> Unit = {},
    val onClearRouteLog: () -> Unit = {},
)

@Composable
fun OperatorScreen(state: OperatorUiState, actions: OperatorActions) {
    Scaffold(containerColor = OperatorColors.Background) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Header(state)
            SubsystemsPanel(state)
            ControlsPanel(state, actions)
            ModePanel(state, actions)
            WitPanel(state, actions)
            AudioTestPanel(state, actions)
            BluetoothPanel(state, actions)
            RouteLogPanel(state, actions)
            DiagnosticsPanel(state)
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun Header(state: OperatorUiState) {
    val status = state.operator.status
    val statusColor = when (status) {
        OperatorStatus.MUTED -> OperatorColors.Alert
        OperatorStatus.ACTIVE -> OperatorColors.Amber
        OperatorStatus.STANDING_BY -> OperatorColors.Cream
        OperatorStatus.OFF -> OperatorColors.CreamDim
    }
    Column(Modifier.fillMaxWidth().padding(top = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("OPERATOR", style = MaterialTheme.typography.displaySmall, color = OperatorColors.Amber, textAlign = TextAlign.Center)
        Text("PRIVATE ASSISTANCE SYSTEM", style = MaterialTheme.typography.titleMedium, color = OperatorColors.CreamDim)
        Spacer(Modifier.height(14.dp))
        Text("STATUS:", style = MaterialTheme.typography.labelSmall, color = OperatorColors.AmberDim)
        Text(status.label, style = MaterialTheme.typography.titleMedium, color = statusColor)
    }
}

@Composable
private fun SubsystemsPanel(state: OperatorUiState) {
    ConsolePanel("Subsystems") {
        Subsystem.entries.forEach { subsystem ->
            val s = state.operator.subsystem(subsystem)
            SubsystemRow(subsystem.label, s.state, s.detail)
        }
    }
}

@Composable
private fun ControlsPanel(state: OperatorUiState, actions: OperatorActions) {
    val muted = state.operator.muted
    val isActive = state.operator.mode.allowsUnsolicitedComments
    ConsolePanel("Controls") {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = actions.onActivate,
                enabled = !isActive,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = OperatorColors.Amber, contentColor = OperatorColors.Background),
            ) { Text("ACTIVATE OPERATOR", style = MaterialTheme.typography.labelSmall) }
            OutlinedButton(
                onClick = actions.onStandby,
                enabled = state.operator.mode != OperatorMode.STANDBY,
                modifier = Modifier.weight(1f),
            ) { Text("STANDBY", style = MaterialTheme.typography.labelSmall) }
        }
        Spacer(Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(
                onClick = actions.onCommentNow,
                enabled = !muted && state.operator.isProcessing,
                modifier = Modifier.weight(1f),
            ) { Text("COMMENT NOW", style = MaterialTheme.typography.labelSmall) }
            Button(
                onClick = actions.onToggleMute,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (muted) OperatorColors.Cream else OperatorColors.Alert,
                    contentColor = OperatorColors.Background,
                ),
            ) { Text(if (muted) "RELEASE MUTE" else "EMERGENCY MUTE", style = MaterialTheme.typography.labelSmall) }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ModePanel(state: OperatorUiState, actions: OperatorActions) {
    ConsolePanel("Mode") {
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            OperatorMode.selectable.forEach { mode ->
                SelectorChip(mode.label, selected = state.operator.mode == mode) { actions.onSelectMode(mode) }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun WitPanel(state: OperatorUiState, actions: OperatorActions) {
    ConsolePanel("Wit") {
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            WitLevel.all.forEach { wit ->
                SelectorChip(wit.label, selected = state.operator.wit == wit) { actions.onSelectWit(wit) }
            }
        }
    }
}

@Composable
private fun SelectorChip(label: String, selected: Boolean, enabled: Boolean = true, onClick: () -> Unit) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        enabled = enabled,
        label = { Text(label, style = MaterialTheme.typography.labelSmall) },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = OperatorColors.Amber,
            selectedLabelColor = OperatorColors.Background,
            labelColor = OperatorColors.Cream,
        ),
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun RouteChips(
    label: String,
    routes: List<AudioRoute>,
    selected: AudioRoute?,
    enabled: Boolean,
    onSelect: (AudioRoute?) -> Unit,
) {
    Text(label, style = MaterialTheme.typography.labelSmall, color = OperatorColors.CreamDim)
    FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
        SelectorChip("DEFAULT", selected = selected == null, enabled = enabled) { onSelect(null) }
        routes.forEach { route ->
            SelectorChip(route.summary.uppercase(), selected = selected?.id == route.id, enabled = enabled) { onSelect(route) }
        }
    }
}

@Composable
private fun AudioTestPanel(state: OperatorUiState, actions: OperatorActions) {
    val loop = state.loopback
    val busy = loop.recordingState.isBusy
    ConsolePanel("Audio test · Milestones 1–2") {
        if (!state.microphonePermissionGranted) {
            Text(
                "Microphone permission is required for RECORD TEST.",
                style = MaterialTheme.typography.bodyMedium,
                color = OperatorColors.CreamDim,
            )
            Spacer(Modifier.height(8.dp))
            Button(onClick = actions.onRequestMicrophone, modifier = Modifier.fillMaxWidth()) {
                Text("GRANT MICROPHONE", style = MaterialTheme.typography.labelSmall)
            }
            Spacer(Modifier.height(8.dp))
        }

        RouteChips("INPUT", state.routes.inputs, loop.selection.input, enabled = !busy, onSelect = actions.onSelectInput)
        Spacer(Modifier.height(6.dp))
        RouteChips("OUTPUT", state.routes.outputs, loop.selection.output, enabled = !busy, onSelect = actions.onSelectOutput)
        if (loop.selection.needsCommunicationLink) {
            Spacer(Modifier.height(4.dp))
            Text(
                if (state.routes.supportsCommunicationDeviceApi) "Bluetooth headset link will be raised via setCommunicationDevice (may take a few seconds)."
                else "Bluetooth headset link needs Android 12+; this device cannot select SCO explicitly.",
                style = MaterialTheme.typography.bodySmall,
                color = OperatorColors.AmberDim,
            )
        }
        Spacer(Modifier.height(10.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = actions.onRecordTest,
                enabled = state.microphonePermissionGranted && !busy && !state.operator.muted,
                modifier = Modifier.weight(1f),
            ) { Text("RECORD TEST", style = MaterialTheme.typography.labelSmall) }
            Button(
                onClick = actions.onPlayTest,
                enabled = loop.hasClip && !busy && !state.operator.muted,
                modifier = Modifier.weight(1f),
            ) { Text("PLAY TEST", style = MaterialTheme.typography.labelSmall) }
        }
        Spacer(Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = actions.onStopAudio, enabled = busy, modifier = Modifier.weight(1f)) {
                Text("STOP", style = MaterialTheme.typography.labelSmall)
            }
            OutlinedButton(onClick = actions.onDiscardClip, enabled = loop.hasClip && !busy, modifier = Modifier.weight(1f)) {
                Text("DISCARD CLIP", style = MaterialTheme.typography.labelSmall)
            }
        }

        if (busy) {
            Spacer(Modifier.height(10.dp))
            val fraction = if (loop.targetMillis > 0) (loop.progressMillis.toFloat() / loop.targetMillis).coerceIn(0f, 1f) else 0f
            LinearProgressIndicator(
                progress = { fraction },
                modifier = Modifier.fillMaxWidth(),
                color = OperatorColors.Amber,
                trackColor = OperatorColors.Outline,
            )
        }

        Spacer(Modifier.height(10.dp))
        val stateColor = when (loop.recordingState) {
            RecordingState.RECORDING, RecordingState.PLAYING -> OperatorColors.Amber
            RecordingState.RECORDED -> OperatorColors.Signal
            RecordingState.ERROR -> OperatorColors.Alert
            RecordingState.IDLE -> OperatorColors.Cream
        }
        KeyValueRow("Recording state", loop.recordingState.label, stateColor)
        KeyValueRow("Progress", "${loop.progressMillis} / ${loop.targetMillis} ms")
        KeyValueRow("Input (selected)", loop.selection.describeInput)
        KeyValueRow("Input (actual)", loop.lastInputRoute?.summary ?: "— (record to detect)")
        loop.lastCaptureNote?.let { KeyValueRow("Capture path", it) }
        KeyValueRow("Output (selected)", loop.selection.describeOutput)
        KeyValueRow("Output (actual)", loop.lastOutputRoute?.summary ?: "— (play to detect)")
        loop.lastPlaybackNote?.let { KeyValueRow("Playback path", it) }
        loop.clipDurationMillis?.let { KeyValueRow("Clip", "${it} ms · peak ${"%.0f".format((loop.clipPeakLevel ?: 0f) * 100)}%") }
        loop.error?.let { KeyValueRow("Error", it, OperatorColors.Alert) }
    }
}

@Composable
private fun BluetoothPanel(state: OperatorUiState, actions: OperatorActions) {
    val routes = state.routes
    ConsolePanel("Bluetooth diagnostics · Milestone 2") {
        if (state.bluetoothPermissionIsRuntime && !state.bluetoothPermissionGranted) {
            Text(
                "BLUETOOTH_CONNECT is needed to list paired devices by name.",
                style = MaterialTheme.typography.bodyMedium,
                color = OperatorColors.CreamDim,
            )
            Spacer(Modifier.height(8.dp))
            Button(onClick = actions.onRequestBluetooth, modifier = Modifier.fillMaxWidth()) {
                Text("GRANT BLUETOOTH", style = MaterialTheme.typography.labelSmall)
            }
            Spacer(Modifier.height(8.dp))
        }
        KeyValueRow("Adapter", state.bluetooth.summary)
        state.bluetooth.bondedDevices?.forEach { d ->
            KeyValueRow(if (d.isAudio) "  paired · audio" else "  paired", "${d.name} · ${d.address}")
        }
        Spacer(Modifier.height(6.dp))
        KeyValueRow("Audio mode", routes.audioMode)
        KeyValueRow("Communication API", if (routes.supportsCommunicationDeviceApi) "setCommunicationDevice (API 31+)" else "unavailable below API 31")
        KeyValueRow("Active comm device", routes.activeCommunicationDevice?.summary ?: "none")
        KeyValueRow("Comm-capable outputs", routes.communicationDevices.joinToString { it.summary }.ifEmpty { "none" })
        Spacer(Modifier.height(6.dp))
        Text("INPUT DEVICES", style = MaterialTheme.typography.labelSmall, color = OperatorColors.AmberDim)
        if (routes.inputs.isEmpty()) KeyValueRow("—", "none")
        routes.inputs.forEach { r -> KeyValueRow("#${r.id} ${r.summary}", r.capabilities, if (r.isBluetooth) OperatorColors.Amber else OperatorColors.Cream) }
        Spacer(Modifier.height(6.dp))
        Text("OUTPUT DEVICES", style = MaterialTheme.typography.labelSmall, color = OperatorColors.AmberDim)
        if (routes.outputs.isEmpty()) KeyValueRow("—", "none")
        routes.outputs.forEach { r -> KeyValueRow("#${r.id} ${r.summary}", r.capabilities, if (r.isBluetooth) OperatorColors.Amber else OperatorColors.Cream) }
        Spacer(Modifier.height(8.dp))
        OutlinedButton(onClick = actions.onRefresh, modifier = Modifier.fillMaxWidth()) {
            Text("REFRESH DEVICES", style = MaterialTheme.typography.labelSmall)
        }
    }
}

private val timeFormat = SimpleDateFormat("HH:mm:ss.SSS", Locale.US)

@Composable
private fun RouteLogPanel(state: OperatorUiState, actions: OperatorActions) {
    ConsolePanel("Route event log") {
        if (state.routeEvents.isEmpty()) {
            Text("No events yet.", style = MaterialTheme.typography.bodySmall, color = OperatorColors.CreamDim)
        } else {
            state.routeEvents.asReversed().take(25).forEach { e ->
                Text(
                    "${timeFormat.format(Date(e.atMillis))}  ${e.message}",
                    style = MaterialTheme.typography.bodySmall,
                    color = OperatorColors.Cream,
                    modifier = Modifier.padding(vertical = 1.dp),
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        OutlinedButton(onClick = actions.onClearRouteLog, enabled = state.routeEvents.isNotEmpty(), modifier = Modifier.fillMaxWidth()) {
            Text("CLEAR LOG", style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
private fun DiagnosticsPanel(state: OperatorUiState) {
    val c = state.config
    ConsolePanel("Diagnostics") {
        KeyValueRow("App version", state.appVersion)
        KeyValueRow("Mode", state.operator.mode.label)
        KeyValueRow("Wit", state.operator.wit.label)
        KeyValueRow("Muted", if (state.operator.muted) "YES" else "no")
        KeyValueRow("May volunteer", if (state.operator.mayVolunteer) "yes" else "no")
        KeyValueRow("Record test length", "${c.recordTestDurationMillis} ms")
        KeyValueRow("Backend URL", c.backendUrl ?: "— (Milestone 4)")
        KeyValueRow("AI provider / model", c.fastModelId ?: "— (Milestone 6)")
        KeyValueRow("Decision model", c.decisionModelId ?: "— (Milestone 12)")
        KeyValueRow("TTS provider", c.ttsProvider ?: "— (Milestone 8)")
        KeyValueRow("Voice ID", c.elevenLabsVoiceId ?: "— (Milestone 9)")
        KeyValueRow("Glasses", "— (Milestone 3)")
        KeyValueRow("Remote controller", "— (Milestone 15)")
        KeyValueRow("Last event", state.lastEvent ?: "—")
    }
}
