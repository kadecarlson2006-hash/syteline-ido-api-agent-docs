package com.operator.core.decision

/**
 * Contract for the decision stage (Milestone 12). Given conversation context, decide whether
 * Operator should speak at all, and if so what. Implementations must treat NO_RESPONSE as the
 * default and must be safe to call frequently.
 *
 * The request type is intentionally minimal for now; it will grow a rolling transcript,
 * retrieved memories, and trigger information when those subsystems exist.
 */
interface ResponseDecisionEngine {
    suspend fun decide(request: DecisionRequest): ResponseDecision
}

data class DecisionRequest(
    val trigger: DecisionTrigger,
    val recentTranscript: String = "",
)

enum class DecisionTrigger { AMBIENT, DIRECT_ADDRESS, COMMENT_NOW }

/** Engine used until Milestone 12: always silent. Makes "silence is the default" literal. */
object SilentDecisionEngine : ResponseDecisionEngine {
    override suspend fun decide(request: DecisionRequest): ResponseDecision =
        ResponseDecision.silence(reasonCode = "ENGINE_NOT_IMPLEMENTED")
}
