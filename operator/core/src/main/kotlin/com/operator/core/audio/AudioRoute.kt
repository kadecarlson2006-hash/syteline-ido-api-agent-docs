package com.operator.core.audio

/**
 * Platform-neutral description of an audio endpoint. The Android layer maps
 * `AudioDeviceInfo` into this so that UI and diagnostics never depend on Android types.
 */
data class AudioRoute(
    val id: Int,
    val name: String,
    val kind: AudioRouteKind,
    val isSource: Boolean,
    val isSink: Boolean,
    val sampleRates: List<Int> = emptyList(),
    val channelCounts: List<Int> = emptyList(),
    /** Encoding labels such as PCM16, PCM_FLOAT. Empty means the platform did not say. */
    val encodings: List<String> = emptyList(),
    /** Platform address (Bluetooth MAC for Bluetooth devices), if exposed. */
    val address: String? = null,
) {
    val isBluetooth: Boolean get() = kind.isBluetooth

    /** True for Bluetooth links that must be brought up as a "communication device" before use (SCO / LE headset). */
    val usesCommunicationLink: Boolean get() = kind.isCommunicationLink

    val summary: String get() = "$name (${kind.label})"

    /** One-line capability description for diagnostics. */
    val capabilities: String
        get() = buildString {
            append(if (isSource && isSink) "in/out" else if (isSource) "in" else if (isSink) "out" else "-")
            if (sampleRates.isNotEmpty()) append(" · ").append(sampleRates.joinToString("/")).append(" Hz")
            if (channelCounts.isNotEmpty()) append(" · ch ").append(channelCounts.joinToString("/"))
            if (encodings.isNotEmpty()) append(" · ").append(encodings.joinToString("/"))
            if (!address.isNullOrBlank()) append(" · ").append(address)
        }
}

enum class AudioRouteKind(
    val label: String,
    val isBluetooth: Boolean = false,
    val isCommunicationLink: Boolean = false,
) {
    BUILTIN_MIC("built-in mic"),
    BUILTIN_SPEAKER("built-in speaker"),
    BUILTIN_EARPIECE("earpiece"),
    WIRED_HEADSET("wired headset"),
    WIRED_HEADPHONES("wired headphones"),
    USB("USB"),
    BLUETOOTH_SCO("Bluetooth SCO", isBluetooth = true, isCommunicationLink = true),
    BLUETOOTH_A2DP("Bluetooth A2DP", isBluetooth = true),
    BLE_HEADSET("BLE headset", isBluetooth = true, isCommunicationLink = true),
    BLE_SPEAKER("BLE speaker", isBluetooth = true),
    BLE_BROADCAST("BLE broadcast", isBluetooth = true),
    HEARING_AID("hearing aid", isBluetooth = true),
    TELEPHONY("telephony"),
    REMOTE_SUBMIX("remote submix"),
    UNKNOWN("unknown"),
}
