package com.operator.core.model

/**
 * The operating posture of Operator. Mode governs *whether and when* Operator is
 * allowed to participate; [WitLevel] governs *how much humor* it brings when it does.
 *
 * SILENCE IS THE DEFAULT in every mode. Even CHAOS produces NO_RESPONSE most of the time.
 */
enum class OperatorMode(
    /** Short label shown in the UI. */
    val label: String,
    /** Whether Operator may speak without being directly addressed. */
    val allowsUnsolicitedComments: Boolean,
    /** Whether Operator listens to the conversation at all. */
    val listens: Boolean,
) {
    /** No Operator processing whatsoever. */
    OFF(label = "OFF", allowsUnsolicitedComments = false, listens = false),

    /** Available when directly addressed. No voluntary participation. */
    STANDBY(label = "STANDBY", allowsUnsolicitedComments = false, listens = true),

    /** Follows the conversation; may occasionally offer information, corrections, reminders, humor. */
    ACTIVE(label = "ACTIVE", allowsUnsolicitedComments = true, listens = true),

    /** Business information, numbers, commitments, risks, deadlines. Reduced humor. */
    WORK(label = "WORK", allowsUnsolicitedComments = true, listens = true),

    /** Names, conversation prompts, subtle social assistance, appropriate humor. */
    SOCIAL(label = "SOCIAL", allowsUnsolicitedComments = true, listens = true),

    /** Only responds when explicitly addressed or manually triggered. */
    QUIET(label = "QUIET", allowsUnsolicitedComments = false, listens = true),

    /** Lower humor threshold, more sarcasm and callbacks. All safety/privacy rules still apply. */
    CHAOS(label = "CHAOS", allowsUnsolicitedComments = true, listens = true);

    /** Modes the user can pick from the main-screen selector (OFF is reached via its own control). */
    companion object {
        val selectable: List<OperatorMode> = listOf(STANDBY, ACTIVE, WORK, SOCIAL, QUIET, CHAOS)
    }
}
