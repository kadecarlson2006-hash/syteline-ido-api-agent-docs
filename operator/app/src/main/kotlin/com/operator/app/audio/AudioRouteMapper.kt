package com.operator.app.audio

import android.media.AudioDeviceInfo
import com.operator.core.audio.AudioRoute
import com.operator.core.audio.AudioRouteKind

/** Maps Android [AudioDeviceInfo] to the platform-neutral [AudioRoute]. */
object AudioRouteMapper {

    fun toRoute(info: AudioDeviceInfo): AudioRoute = AudioRoute(
        id = info.id,
        name = info.productName?.toString()?.takeIf { it.isNotBlank() } ?: kindOf(info.type).label,
        kind = kindOf(info.type),
        isSource = info.isSource,
        isSink = info.isSink,
        sampleRates = info.sampleRates.toList(),
        channelCounts = info.channelCounts.toList(),
    )

    /** Pure function over the integer device type so it can be unit-tested without Android. */
    fun kindOf(type: Int): AudioRouteKind = when (type) {
        AudioDeviceInfo.TYPE_BUILTIN_MIC -> AudioRouteKind.BUILTIN_MIC
        AudioDeviceInfo.TYPE_BUILTIN_SPEAKER,
        AudioDeviceInfo.TYPE_BUILTIN_SPEAKER_SAFE -> AudioRouteKind.BUILTIN_SPEAKER
        AudioDeviceInfo.TYPE_BUILTIN_EARPIECE -> AudioRouteKind.BUILTIN_EARPIECE
        AudioDeviceInfo.TYPE_WIRED_HEADSET -> AudioRouteKind.WIRED_HEADSET
        AudioDeviceInfo.TYPE_WIRED_HEADPHONES -> AudioRouteKind.WIRED_HEADPHONES
        AudioDeviceInfo.TYPE_USB_DEVICE,
        AudioDeviceInfo.TYPE_USB_ACCESSORY,
        AudioDeviceInfo.TYPE_USB_HEADSET -> AudioRouteKind.USB
        AudioDeviceInfo.TYPE_BLUETOOTH_SCO -> AudioRouteKind.BLUETOOTH_SCO
        AudioDeviceInfo.TYPE_BLUETOOTH_A2DP -> AudioRouteKind.BLUETOOTH_A2DP
        AudioDeviceInfo.TYPE_HEARING_AID -> AudioRouteKind.HEARING_AID
        AudioDeviceInfo.TYPE_TELEPHONY -> AudioRouteKind.TELEPHONY
        AudioDeviceInfo.TYPE_REMOTE_SUBMIX -> AudioRouteKind.REMOTE_SUBMIX
        // Constants below were added after minSdk 29; they are compile-time constants so
        // referencing them is safe on older devices (the value simply never occurs there).
        AudioDeviceInfo.TYPE_BLE_HEADSET -> AudioRouteKind.BLE_HEADSET
        AudioDeviceInfo.TYPE_BLE_SPEAKER -> AudioRouteKind.BLE_SPEAKER
        AudioDeviceInfo.TYPE_BLE_BROADCAST -> AudioRouteKind.BLE_BROADCAST
        else -> AudioRouteKind.UNKNOWN
    }
}
