package com.operator.app.config

import com.operator.app.BuildConfig
import com.operator.core.config.OperatorConfig

/**
 * Turns BuildConfig fields (populated from local.properties / environment at build time)
 * into an [OperatorConfig]. Non-secret values only.
 */
object BuildConfigLoader {
    fun load(): OperatorConfig = OperatorConfig.fromMap(
        mapOf(
            OperatorConfig.Keys.DEFAULT_MODE to BuildConfig.OPERATOR_DEFAULT_MODE,
            OperatorConfig.Keys.DEFAULT_WIT to BuildConfig.OPERATOR_DEFAULT_WIT,
            OperatorConfig.Keys.BACKEND_URL to BuildConfig.OPERATOR_BACKEND_URL,
            OperatorConfig.Keys.FAST_MODEL_ID to BuildConfig.OPERATOR_FAST_MODEL_ID,
            OperatorConfig.Keys.DEEP_MODEL_ID to BuildConfig.OPERATOR_DEEP_MODEL_ID,
            OperatorConfig.Keys.DECISION_MODEL_ID to BuildConfig.OPERATOR_DECISION_MODEL_ID,
            OperatorConfig.Keys.VISION_MODEL_ID to BuildConfig.OPERATOR_VISION_MODEL_ID,
            OperatorConfig.Keys.TTS_PROVIDER to BuildConfig.OPERATOR_TTS_PROVIDER,
            OperatorConfig.Keys.ELEVENLABS_VOICE_ID to BuildConfig.OPERATOR_ELEVENLABS_VOICE_ID,
            OperatorConfig.Keys.ELEVENLABS_MODEL_ID to BuildConfig.OPERATOR_ELEVENLABS_MODEL_ID,
            OperatorConfig.Keys.ROLLING_CONTEXT_SECONDS to BuildConfig.ROLLING_CONTEXT_SECONDS,
            OperatorConfig.Keys.MIN_COMMENT_INTERVAL_SECONDS to BuildConfig.MIN_COMMENT_INTERVAL_SECONDS,
            OperatorConfig.Keys.MAX_COMMENTS_PER_5_MINUTES to BuildConfig.MAX_COMMENTS_PER_5_MINUTES,
            OperatorConfig.Keys.RECORD_TEST_DURATION_MILLIS to BuildConfig.OPERATOR_RECORD_TEST_DURATION_MILLIS,
        ),
    )
}
