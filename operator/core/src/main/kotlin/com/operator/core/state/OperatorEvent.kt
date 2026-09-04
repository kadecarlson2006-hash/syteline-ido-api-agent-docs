package com.operator.core.state

import com.operator.core.model.OperatorMode

/**
 * One-shot events emitted by [OperatorStateManager] for other subsystems to react to.
 * These are *not* state; they are signals (a button press, a mode change).
 */
sealed interface OperatorEvent {
    /** The user asked Operator to comment on the recent conversation right now (button/ring). */
    data object CommentNowRequested : OperatorEvent

    /** Emergency mute engaged. Any in-flight speech must stop immediately. */
    data object EmergencyMuteEngaged : OperatorEvent

    /** Mute released. */
    data object MuteReleased : OperatorEvent

    data class ModeChanged(val from: OperatorMode, val to: OperatorMode) : OperatorEvent
}
