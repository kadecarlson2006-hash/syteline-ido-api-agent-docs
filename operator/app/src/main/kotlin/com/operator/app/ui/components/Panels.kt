package com.operator.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.operator.app.ui.theme.OperatorColors
import com.operator.core.model.SubsystemState

/** A bordered panel with a small monospace caption, in the style of a control console. */
@Composable
fun ConsolePanel(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(containerColor = OperatorColors.Surface),
        border = BorderStroke(1.dp, OperatorColors.Outline),
    ) {
        Column(Modifier.padding(horizontal = 14.dp, vertical = 12.dp)) {
            Text(
                text = title.uppercase(),
                style = MaterialTheme.typography.labelSmall,
                color = OperatorColors.AmberDim,
            )
            Spacer(Modifier.height(8.dp))
            content()
        }
    }
}

/** Label on the left, value on the right; the basic diagnostics row. */
@Composable
fun KeyValueRow(label: String, value: String, valueColor: Color = OperatorColors.Cream) {
    Row(
        Modifier.fillMaxWidth().padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(label, style = MaterialTheme.typography.bodySmall, color = OperatorColors.CreamDim)
        Text(
            value,
            style = MaterialTheme.typography.bodySmall,
            color = valueColor,
            modifier = Modifier.padding(start = 12.dp),
        )
    }
}

/** Indicator light + label + state, for the subsystem list. */
@Composable
fun SubsystemRow(label: String, state: SubsystemState, detail: String?) {
    val light = when (state) {
        SubsystemState.NOT_IMPLEMENTED -> OperatorColors.Outline
        SubsystemState.NOT_CONFIGURED -> OperatorColors.AmberDim
        SubsystemState.UNAVAILABLE -> OperatorColors.AmberDim
        SubsystemState.READY -> OperatorColors.Signal
        SubsystemState.ACTIVE -> OperatorColors.Amber
        SubsystemState.ERROR -> OperatorColors.Alert
    }
    Row(
        Modifier.fillMaxWidth().padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(
            Modifier
                .size(10.dp)
                .background(light, CircleShape),
        )
        Spacer(Modifier.size(10.dp))
        Text(label, style = MaterialTheme.typography.bodyMedium, color = OperatorColors.Cream, modifier = Modifier.weight(1f))
        Column(horizontalAlignment = Alignment.End) {
            Text(state.label, style = MaterialTheme.typography.labelSmall, color = light)
            if (!detail.isNullOrBlank()) {
                Text(detail, style = MaterialTheme.typography.bodySmall, color = OperatorColors.CreamDim)
            }
        }
    }
}
