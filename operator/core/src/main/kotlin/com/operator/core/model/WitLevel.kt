package com.operator.core.model

/**
 * Humor intensity, configurable independently of [OperatorMode].
 *
 * Wit never overrides safety, truthfulness, or privacy rules. It only lowers or raises the
 * threshold at which a HUMOR-category response is permitted.
 */
enum class WitLevel(val label: String) {
    OFF("OFF"),
    DRY("DRY"),
    NORMAL("NORMAL"),
    SHARP("SHARP"),
    UNHINGED("UNHINGED");

    companion object {
        val all: List<WitLevel> = entries
    }
}
