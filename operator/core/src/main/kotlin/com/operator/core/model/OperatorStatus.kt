package com.operator.core.model

/**
 * Coarse runtime status shown as the headline on the main screen ("STATUS: STANDING BY").
 * This is derived from [OperatorState]; it is not independently settable.
 */
enum class OperatorStatus(val label: String) {
    OFF("OFF"),
    STANDING_BY("STANDING BY"),
    ACTIVE("ACTIVE"),
    MUTED("MUTED"),
}
