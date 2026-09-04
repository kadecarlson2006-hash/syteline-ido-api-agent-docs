package com.operator.core.decision

/** Why Operator would (or would not) speak. NO_RESPONSE is the expected common case. */
enum class ResponseCategory {
    DIRECT_REQUEST,
    FACT_CORRECTION,
    USEFUL_CONTEXT,
    MEMORY_REMINDER,
    SOCIAL_ASSIST,
    WORK_ASSIST,
    HUMOR,
    WARNING,
    NO_RESPONSE,
}

/**
 * Structured output of the (future) decision model plus local suppression rules.
 *
 * [reasonCode] holds a short diagnostic label only (e.g. DIRECT_ADDRESS, LOW_CONFIDENCE,
 * RECENTLY_SPOKE). Internal model reasoning is never surfaced to the user.
 */
data class ResponseDecision(
    val shouldSpeak: Boolean,
    val category: ResponseCategory,
    val confidence: Float,
    val urgency: Float,
    val relevance: Float,
    val response: String?,
    val reasonCode: String?,
) {
    init {
        require(confidence in 0f..1f) { "confidence out of range" }
        require(urgency in 0f..1f) { "urgency out of range" }
        require(relevance in 0f..1f) { "relevance out of range" }
        if (shouldSpeak) require(!response.isNullOrBlank()) { "shouldSpeak requires a response" }
    }

    /** Returns a copy marked as suppressed with the given reason. Response suppression is first-class. */
    fun suppressed(reasonCode: String): ResponseDecision =
        copy(shouldSpeak = false, reasonCode = reasonCode)

    companion object {
        /** The most common outcome. */
        fun silence(reasonCode: String = "NO_RESPONSE"): ResponseDecision = ResponseDecision(
            shouldSpeak = false,
            category = ResponseCategory.NO_RESPONSE,
            confidence = 1f,
            urgency = 0f,
            relevance = 0f,
            response = null,
            reasonCode = reasonCode,
        )
    }
}
