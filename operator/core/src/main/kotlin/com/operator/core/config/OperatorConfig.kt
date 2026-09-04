package com.operator.core.config

import com.operator.core.model.OperatorMode
import com.operator.core.model.WitLevel

/**
 * Centralized, immutable runtime configuration.
 *
 * Values originate from `local.properties` / environment (via the app's BuildConfig) or a
 * backend-provided config in later milestones. Secrets are deliberately NOT part of this
 * class: provider API keys belong to the backend (see docs/DECISIONS.md ADR-005).
 *
 * Model IDs, voice IDs, and provider names are nullable strings on purpose: nothing is
 * hard-coded, and "not configured" is a first-class state that the UI can display.
 */
data class OperatorConfig(
    val defaultMode: OperatorMode = OperatorMode.STANDBY,
    val defaultWit: WitLevel = WitLevel.NORMAL,

    /** Base URL of the Operator backend (Milestone 4+). */
    val backendUrl: String? = null,

    // Model routing (Milestone 6+). Never hard-code model names.
    val fastModelId: String? = null,
    val deepModelId: String? = null,
    val decisionModelId: String? = null,
    val visionModelId: String? = null,

    // Voice (Milestone 8/9).
    val ttsProvider: String? = null,
    val elevenLabsVoiceId: String? = null,
    val elevenLabsModelId: String? = null,

    // Conversation / anti-annoyance (Milestone 11+).
    val rollingContextSeconds: Int = 60,
    val minCommentIntervalSeconds: Int = 45,
    val maxCommentsPer5Minutes: Int = 3,

    // Milestone 1 audio test.
    val recordTestDurationMillis: Long = 4_000,
) {
    init {
        require(rollingContextSeconds > 0) { "rollingContextSeconds must be > 0" }
        require(minCommentIntervalSeconds >= 0) { "minCommentIntervalSeconds must be >= 0" }
        require(maxCommentsPer5Minutes >= 0) { "maxCommentsPer5Minutes must be >= 0" }
        require(recordTestDurationMillis in 1_000..30_000) { "recordTestDurationMillis must be 1s..30s" }
    }

    companion object {
        /**
         * Builds a config from a flat key/value map (e.g. BuildConfig fields, a .env file, or
         * system properties). Unknown keys are ignored; malformed numbers fall back to defaults.
         * Keys use the OPERATOR_* naming from the project spec.
         */
        fun fromMap(values: Map<String, String?>): OperatorConfig {
            val defaults = OperatorConfig()
            fun str(key: String): String? = values[key]?.trim()?.takeIf { it.isNotEmpty() }
            fun int(key: String, default: Int): Int = str(key)?.toIntOrNull() ?: default
            fun long(key: String, default: Long): Long = str(key)?.toLongOrNull() ?: default
            return OperatorConfig(
                defaultMode = str(Keys.DEFAULT_MODE)?.let { v -> OperatorMode.entries.firstOrNull { it.name.equals(v, ignoreCase = true) } } ?: defaults.defaultMode,
                defaultWit = str(Keys.DEFAULT_WIT)?.let { v -> WitLevel.entries.firstOrNull { it.name.equals(v, ignoreCase = true) } } ?: defaults.defaultWit,
                backendUrl = str(Keys.BACKEND_URL),
                fastModelId = str(Keys.FAST_MODEL_ID),
                deepModelId = str(Keys.DEEP_MODEL_ID),
                decisionModelId = str(Keys.DECISION_MODEL_ID),
                visionModelId = str(Keys.VISION_MODEL_ID),
                ttsProvider = str(Keys.TTS_PROVIDER),
                elevenLabsVoiceId = str(Keys.ELEVENLABS_VOICE_ID),
                elevenLabsModelId = str(Keys.ELEVENLABS_MODEL_ID),
                rollingContextSeconds = int(Keys.ROLLING_CONTEXT_SECONDS, defaults.rollingContextSeconds),
                minCommentIntervalSeconds = int(Keys.MIN_COMMENT_INTERVAL_SECONDS, defaults.minCommentIntervalSeconds),
                maxCommentsPer5Minutes = int(Keys.MAX_COMMENTS_PER_5_MINUTES, defaults.maxCommentsPer5Minutes),
                recordTestDurationMillis = long(Keys.RECORD_TEST_DURATION_MILLIS, defaults.recordTestDurationMillis),
            )
        }
    }

    /** Canonical configuration key names. Shared by local.properties, .env, and BuildConfig. */
    object Keys {
        const val DEFAULT_MODE = "OPERATOR_DEFAULT_MODE"
        const val DEFAULT_WIT = "OPERATOR_DEFAULT_WIT"
        const val BACKEND_URL = "OPERATOR_BACKEND_URL"
        const val FAST_MODEL_ID = "OPERATOR_FAST_MODEL_ID"
        const val DEEP_MODEL_ID = "OPERATOR_DEEP_MODEL_ID"
        const val DECISION_MODEL_ID = "OPERATOR_DECISION_MODEL_ID"
        const val VISION_MODEL_ID = "OPERATOR_VISION_MODEL_ID"
        const val TTS_PROVIDER = "OPERATOR_TTS_PROVIDER"
        const val ELEVENLABS_VOICE_ID = "OPERATOR_ELEVENLABS_VOICE_ID"
        const val ELEVENLABS_MODEL_ID = "OPERATOR_ELEVENLABS_MODEL_ID"
        const val ROLLING_CONTEXT_SECONDS = "ROLLING_CONTEXT_SECONDS"
        const val MIN_COMMENT_INTERVAL_SECONDS = "MIN_COMMENT_INTERVAL_SECONDS"
        const val MAX_COMMENTS_PER_5_MINUTES = "MAX_COMMENTS_PER_5_MINUTES"
        const val RECORD_TEST_DURATION_MILLIS = "OPERATOR_RECORD_TEST_DURATION_MILLIS"
    }
}
