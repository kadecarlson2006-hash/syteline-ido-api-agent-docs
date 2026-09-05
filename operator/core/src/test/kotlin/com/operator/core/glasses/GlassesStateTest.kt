package com.operator.core.glasses

import com.operator.core.model.SubsystemState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GlassesStateTest {
    private val base = GlassesState(providerName = "test", sdkPresent = true, initialized = true)
    private val connected = GlassesDevice("1", "Ray-Ban Meta", "RAYBAN_META", "CONNECTED", "COMPATIBLE", false)
    private val away = GlassesDevice("2", "Old pair", "RAYBAN_META", "DISCONNECTED", "COMPATIBLE", false)

    @Test
    fun `no sdk is not configured`() {
        val s = NoGlassesProvider().state.value.toSubsystemStatus()
        assertEquals(SubsystemState.NOT_CONFIGURED, s.state)
        assertTrue(NoGlassesProvider().actions.isEmpty())
    }

    @Test
    fun `registration gates readiness`() {
        assertEquals(SubsystemState.NOT_CONFIGURED, base.copy(registration = GlassesRegistration.AVAILABLE).toSubsystemStatus().state)
        assertEquals(SubsystemState.UNAVAILABLE, base.copy(registration = GlassesRegistration.REGISTERED).toSubsystemStatus().state)
    }

    @Test
    fun `connected device is ready and a started session is active`() {
        val registered = base.copy(registration = GlassesRegistration.REGISTERED, devices = listOf(away, connected))
        assertEquals(SubsystemState.READY, registered.toSubsystemStatus().state)
        assertEquals("Ray-Ban Meta", registered.toSubsystemStatus().detail)
        assertEquals(SubsystemState.ACTIVE, registered.copy(session = GlassesSessionState.STARTED).toSubsystemStatus().state)
        assertEquals(SubsystemState.UNAVAILABLE, registered.copy(devices = listOf(away)).toSubsystemStatus().state)
    }

    @Test
    fun `initialisation error surfaces as ERROR`() {
        val s = base.copy(initialized = false, lastError = "initialize failed").toSubsystemStatus()
        assertEquals(SubsystemState.ERROR, s.state)
        assertEquals("initialize failed", s.detail)
    }
}
