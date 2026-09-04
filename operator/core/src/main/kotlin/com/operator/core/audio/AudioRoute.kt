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
) {
    val isBluetooth: Boolean get() = kind.isBluetooth
    val summary: String get() = "$name (${kind.label})"
}

enum class AudioRouteKind(val label: String, val isBluetooth: Boolean = false) {
    BUILTIN_MIC("built-in mic"),
    BUILTIN_SPEAKER("built-in speaker"),
    BUILTIN_EARPIECE("earpiece"),
    WIRED_HEADSET("wired headset"),
    WIRED_HEADPHONES("wired headphones"),
    USB("USB"),
    BLUETOOTH_SCO("Bluetooth SCO", isBluetooth = true),
    BLUETOOTH_A2DP("Bluetooth A2DP", isBluetooth = true),
    BLE_HEADSET("BLE headset", isBluetooth = true),
    BLE_SPEAKER("BLE speaker", isBluetooth = true),
    BLE_BROADCAST("BLE broadcast", isBluetooth = true),
    HEARING_AID("hearing aid", isBluetooth = true),
    TELEPHONY("telephony"),
    REMOTE_SUBMIX("remote submix"),
    UNKNOWN("unknown"),
}
