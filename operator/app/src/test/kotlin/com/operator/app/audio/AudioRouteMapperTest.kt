package com.operator.app.audio

import android.media.AudioDeviceInfo
import android.media.AudioFormat
import com.operator.core.audio.AudioRouteKind
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AudioRouteMapperTest {

    @Test
    fun `built-in devices map to built-in kinds`() {
        assertEquals(AudioRouteKind.BUILTIN_MIC, AudioRouteMapper.kindOf(AudioDeviceInfo.TYPE_BUILTIN_MIC))
        assertEquals(AudioRouteKind.BUILTIN_SPEAKER, AudioRouteMapper.kindOf(AudioDeviceInfo.TYPE_BUILTIN_SPEAKER))
        assertEquals(AudioRouteKind.BUILTIN_SPEAKER, AudioRouteMapper.kindOf(AudioDeviceInfo.TYPE_BUILTIN_SPEAKER_SAFE))
        assertEquals(AudioRouteKind.BUILTIN_EARPIECE, AudioRouteMapper.kindOf(AudioDeviceInfo.TYPE_BUILTIN_EARPIECE))
    }

    @Test
    fun `bluetooth devices are flagged as bluetooth and only SCO and LE headset need the communication link`() {
        listOf(
            AudioDeviceInfo.TYPE_BLUETOOTH_SCO,
            AudioDeviceInfo.TYPE_BLUETOOTH_A2DP,
            AudioDeviceInfo.TYPE_BLE_HEADSET,
            AudioDeviceInfo.TYPE_BLE_SPEAKER,
            AudioDeviceInfo.TYPE_BLE_BROADCAST,
            AudioDeviceInfo.TYPE_HEARING_AID,
        ).forEach { type ->
            assertTrue("type $type should be bluetooth", AudioRouteMapper.kindOf(type).isBluetooth)
        }
        assertFalse(AudioRouteMapper.kindOf(AudioDeviceInfo.TYPE_WIRED_HEADSET).isBluetooth)
        assertTrue(AudioRouteMapper.kindOf(AudioDeviceInfo.TYPE_BLUETOOTH_SCO).isCommunicationLink)
        assertTrue(AudioRouteMapper.kindOf(AudioDeviceInfo.TYPE_BLE_HEADSET).isCommunicationLink)
        assertFalse(AudioRouteMapper.kindOf(AudioDeviceInfo.TYPE_BLUETOOTH_A2DP).isCommunicationLink)
    }

    @Test
    fun `usb variants collapse to USB and unknown types do not crash`() {
        assertEquals(AudioRouteKind.USB, AudioRouteMapper.kindOf(AudioDeviceInfo.TYPE_USB_HEADSET))
        assertEquals(AudioRouteKind.USB, AudioRouteMapper.kindOf(AudioDeviceInfo.TYPE_USB_DEVICE))
        assertEquals(AudioRouteKind.UNKNOWN, AudioRouteMapper.kindOf(-42))
    }

    @Test
    fun `encodings get readable labels`() {
        assertEquals("PCM16", AudioRouteMapper.encodingLabel(AudioFormat.ENCODING_PCM_16BIT))
        assertEquals("PCM_FLOAT", AudioRouteMapper.encodingLabel(AudioFormat.ENCODING_PCM_FLOAT))
        assertEquals("enc999", AudioRouteMapper.encodingLabel(999))
    }
}
