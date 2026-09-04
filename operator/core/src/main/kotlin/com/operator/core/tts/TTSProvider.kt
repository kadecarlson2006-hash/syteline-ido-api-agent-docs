package com.operator.core.tts

/**
 * Text-to-speech contract (Milestone 8: SystemTTSProvider, Milestone 9: ElevenLabsTTSProvider).
 * Speech must be cancellable at any moment (see project rule: STOP SPEAKING).
 */
interface TTSProvider {
    val name: String
    suspend fun speak(request: TTSRequest): TTSResult
    fun cancel()
}

data class TTSRequest(val text: String, val voiceId: String? = null, val modelId: String? = null)

data class TTSResult(
    val requestSentMillis: Long,
    val firstAudioMillis: Long?,
    val playbackStartedMillis: Long?,
    val playbackCompletedMillis: Long?,
    val cancelled: Boolean,
)
