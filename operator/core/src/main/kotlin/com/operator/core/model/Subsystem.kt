package com.operator.core.model

/**
 * The subsystems whose health is displayed on the main screen and in diagnostics.
 * Each is a placeholder until the milestone that implements it lands.
 */
enum class Subsystem(val label: String, val implementedInMilestone: Int) {
    GLASSES("Glasses", 3),
    MICROPHONE("Microphone", 1),
    AUDIO_OUTPUT("Audio Output", 1),
    AI("AI", 6),
    VOICE("Voice", 8),
    MEMORY("Memory", 5),
    REMOTE_CONTROLLER("Remote Controller", 15),
}

/** Health of a single subsystem. */
enum class SubsystemState(val label: String) {
    /** Milestone not reached yet; nothing wired. */
    NOT_IMPLEMENTED("NOT IMPLEMENTED"),
    /** Implemented but missing configuration or permission. */
    NOT_CONFIGURED("NOT CONFIGURED"),
    /** Implemented and configured but the resource is currently absent (e.g. no glasses paired). */
    UNAVAILABLE("UNAVAILABLE"),
    READY("READY"),
    ACTIVE("ACTIVE"),
    ERROR("ERROR"),
}

/** A subsystem's state plus an optional short human-readable detail line. */
data class SubsystemStatus(
    val state: SubsystemState,
    val detail: String? = null,
) {
    companion object {
        val NotImplemented = SubsystemStatus(SubsystemState.NOT_IMPLEMENTED)
    }
}
