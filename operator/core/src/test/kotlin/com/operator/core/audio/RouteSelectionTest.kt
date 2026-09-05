package com.operator.core.audio

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RouteSelectionTest {
    private val mic = AudioRoute(1, "Mic", AudioRouteKind.BUILTIN_MIC, isSource = true, isSink = false)
    private val a2dp = AudioRoute(3, "Buds", AudioRouteKind.BLUETOOTH_A2DP, isSource = false, isSink = true)
    private val sco = AudioRoute(4, "Buds", AudioRouteKind.BLUETOOTH_SCO, isSource = true, isSink = false)
    private val ble = AudioRoute(5, "Glasses", AudioRouteKind.BLE_HEADSET, isSource = false, isSink = true)

    @Test
    fun `default selection needs no communication link`() {
        assertFalse(RouteSelection.Default.needsCommunicationLink)
        assertEquals("system default", RouteSelection.Default.describeInput)
    }

    @Test
    fun `A2DP output is plain media routing but SCO and BLE headset need the communication link`() {
        assertFalse(RouteSelection(output = a2dp).needsCommunicationLink)
        assertTrue(RouteSelection(input = sco).needsCommunicationLink)
        assertTrue(RouteSelection(output = ble).needsCommunicationLink)
    }

    @Test
    fun `capabilities line lists what the platform reported`() {
        val r = AudioRoute(9, "X", AudioRouteKind.USB, isSource = true, isSink = true, sampleRates = listOf(48_000), channelCounts = listOf(2), encodings = listOf("PCM16"), address = "usb:1")
        assertEquals("in/out · 48000 Hz · ch 2 · PCM16 · usb:1", r.capabilities)
        assertEquals("in", mic.capabilities)
    }
}
