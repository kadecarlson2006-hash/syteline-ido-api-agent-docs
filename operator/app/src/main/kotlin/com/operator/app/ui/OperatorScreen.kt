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

/** Callbacks the screen can invoke. Kept as a value so previews and tests can pass no-ops. */
data class OperatorActions(
    val onActivate: () -> Unit = {},
    val onStandby: () -> Unit = {},
    val onCommentNow: () -> Unit = {},
    val onToggleMute: () -> Unit = {},
    val onSelectMode: (OperatorMode) -> Unit = {},
    val onSelectWit: (WitLevel) -> Unit = {},
    val onRequestMicrophone: () -> Unit = {},
    val onRecordTest: () -> Unit = {},
    val onPlayTest: () -> Unit = {},
    val onStopAudio: () -> Unit = {},
    val onDiscardClip: () -> Unit = {},
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
private fun SelectorChip(label: String, selected: Boolean, onClick: () -> Unit) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(label, style = MaterialTheme.typography.labelSmall) },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = OperatorColors.Amber,
            selectedLabelColor = OperatorColors.Background,
            labelColor = OperatorColors.Cream,
        ),
    )
}

@Composable
private fun AudioTestPanel(state: OperatorUiState, actions: OperatorActions) {
    val loop = state.loopback
    val busy = loop.recordingState.isBusy
    ConsolePanel("Audio test · Milestone 1") {
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
        KeyValueRow("Input device (actual)", loop.lastInputRoute?.summary ?: "— (record to detect)")
        KeyValueRow("Output device (actual)", loop.lastOutputRoute?.summary ?: "— (play to detect)")
        KeyValueRow("Available inputs", state.routes.inputs.summaryLine())
        KeyValueRow("Available outputs", state.routes.outputs.summaryLine())
        loop.clipDurationMillis?.let { KeyValueRow("Clip", "${it} ms · peak ${"%.0f".format((loop.clipPeakLevel ?: 0f) * 100)}%") }
        loop.error?.let { KeyValueRow("Error", it, OperatorColors.Alert) }
    }
}

private fun List<AudioRoute>.summaryLine(): String =
    if (isEmpty()) "none" else joinToString(", ") { it.summary }

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
