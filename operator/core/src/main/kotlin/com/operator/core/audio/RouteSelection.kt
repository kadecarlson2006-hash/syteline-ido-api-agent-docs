package com.operator.core.audio

/**
 * The user's explicit choice of capture and playback endpoints. `null` means "let the
 * platform decide" (system default routing), which is the Milestone 1 behaviour.
 */
data class RouteSelection(
    val input: AudioRoute? = null,
    val output: AudioRoute? = null,
) {
    /** True when either side is a Bluetooth link that has to be raised as a communication device. */
    val needsCommunicationLink: Boolean
        get() = input?.usesCommunicationLink == true || output?.usesCommunicationLink == true

    /** Drops selections whose device is no longer present (unplugged, Bluetooth disconnected). */
    fun pruned(availableInputs: List<AudioRoute>, availableOutputs: List<AudioRoute>): RouteSelection =
        RouteSelection(
            input = input?.takeIf { sel -> availableInputs.any { it.id == sel.id } },
            output = output?.takeIf { sel -> availableOutputs.any { it.id == sel.id } },
        )

    val describeInput: String get() = input?.summary ?: "system default"
    val describeOutput: String get() = output?.summary ?: "system default"

    companion object {
        val Default = RouteSelection()
    }
}
