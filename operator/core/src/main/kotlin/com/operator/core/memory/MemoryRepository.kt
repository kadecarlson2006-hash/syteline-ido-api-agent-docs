package com.operator.core.memory

/**
 * Memory backend contract (Milestone 5). The real implementation lives behind the Operator
 * backend (PostgreSQL + pgvector, ADR-002). Defined here so the app can compile against a
 * FakeMemoryRepository long before the backend exists.
 */
interface MemoryRepository {
    suspend fun search(query: MemoryQuery): List<MemoryRecord>
    suspend fun store(candidate: MemoryCandidate): MemoryRecord
    suspend fun delete(id: String): Boolean
}

enum class MemoryType {
    PERSONAL_PROFILE, PERSON, ORGANIZATION, WORK_FACT, PROJECT, PREFERENCE, GOAL,
    DECISION, COMMITMENT, EPISODIC_EVENT, SESSION_MEMORY, LONG_TERM_MEMORY,
}

enum class PrivacyScope { PERSONAL, WORK, SESSION, PRIVATE, RESTRICTED }

data class MemoryQuery(val text: String, val limit: Int = 5, val scopes: Set<PrivacyScope> = PrivacyScope.entries.toSet())

data class MemoryCandidate(
    val type: MemoryType,
    val content: String,
    val scope: PrivacyScope,
    val importance: Float = 0.5f,
    val confidence: Float = 0.5f,
)

data class MemoryRecord(
    val id: String,
    val type: MemoryType,
    val content: String,
    val scope: PrivacyScope,
    val importance: Float,
    val confidence: Float,
)
