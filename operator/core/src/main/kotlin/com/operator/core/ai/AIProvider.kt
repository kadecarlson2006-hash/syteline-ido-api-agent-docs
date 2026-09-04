package com.operator.core.ai

/**
 * Model gateway contract (Milestone 6). First implementation: OpenRouterProvider (backend side).
 * Kept deliberately small; fields will grow with streaming, tools, and usage accounting.
 */
interface AIProvider {
    suspend fun generate(request: AIRequest): AIResponse
}

data class AIRequest(
    val modelId: String,
    val systemPrompt: String,
    val userContent: String,
    val maxOutputTokens: Int? = null,
)

data class AIResponse(
    val text: String,
    val modelId: String,
    val inputTokens: Int? = null,
    val outputTokens: Int? = null,
    val latencyMillis: Long? = null,
)
