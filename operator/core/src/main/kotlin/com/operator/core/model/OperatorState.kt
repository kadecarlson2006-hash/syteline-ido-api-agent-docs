package com.operator.core.model

/**
 * The complete, immutable snapshot of Operator's user-facing state.
 *
 * Owned and mutated only by [com.operator.core.state.OperatorStateManager].
 */
data class OperatorState(
    val mode: OperatorMode,
    val wit: WitLevel,
    /** Emergency mute. When true Operator must not produce audio regardless of mode. */
    val muted: Boolean = false,
    val subsystems: Map<Subsystem, SubsystemStatus> = Subsystem.entries.associateWith { SubsystemStatus.NotImplemented },
) {
    /** Headline status derived from mode + mute. */
    val status: OperatorStatus
        get() = when {
            muted -> OperatorStatus.MUTED
            mode == OperatorMode.OFF -> OperatorStatus.OFF
            mode == OperatorMode.STANDBY || mode == OperatorMode.QUIET -> OperatorStatus.STANDING_BY
            else -> OperatorStatus.ACTIVE
        }

    /** True when Operator is in a mode that permits volunteering a comment and is not muted. */
    val mayVolunteer: Boolean
        get() = !muted && mode.allowsUnsolicitedComments

    /** True when Operator is doing any processing at all. */
    val isProcessing: Boolean
        get() = mode != OperatorMode.OFF

    fun subsystem(subsystem: Subsystem): SubsystemStatus =
        subsystems[subsystem] ?: SubsystemStatus.NotImplemented
}
