package com.operator.core.state

import app.cash.turbine.test
import com.operator.core.model.OperatorMode
import com.operator.core.model.OperatorStatus
import com.operator.core.model.Subsystem
import com.operator.core.model.SubsystemState
import com.operator.core.model.SubsystemStatus
import com.operator.core.model.WitLevel
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class OperatorStateManagerTest {

    private fun manager(mode: OperatorMode = OperatorMode.STANDBY, wit: WitLevel = WitLevel.NORMAL) =
        OperatorStateManager(initialMode = mode, initialWit = wit)

    @Test
    fun `initial state is standing by with default subsystems not implemented`() {
        val m = manager()
        assertEquals(OperatorStatus.STANDING_BY, m.current.status)
        assertEquals(WitLevel.NORMAL, m.current.wit)
        assertFalse(m.current.muted)
        Subsystem.entries.forEach {
            assertEquals(SubsystemState.NOT_IMPLEMENTED, m.current.subsystem(it).state)
        }
    }

    @Test
    fun `activate and standby switch status`() {
        val m = manager()
        m.activate()
        assertEquals(OperatorStatus.ACTIVE, m.current.status)
        assertTrue(m.current.mayVolunteer)
        m.standby()
        assertEquals(OperatorStatus.STANDING_BY, m.current.status)
        assertFalse(m.current.mayVolunteer)
    }

    @Test
    fun `quiet mode stands by and never volunteers`() {
        val m = manager()
        m.setMode(OperatorMode.QUIET)
        assertEquals(OperatorStatus.STANDING_BY, m.current.status)
        assertFalse(m.current.mayVolunteer)
    }

    @Test
    fun `emergency mute overrides every mode and survives mode changes`() {
        val m = manager(OperatorMode.CHAOS)
        m.emergencyMute()
        assertEquals(OperatorStatus.MUTED, m.current.status)
        assertFalse(m.current.mayVolunteer)
        m.setMode(OperatorMode.ACTIVE)
        assertTrue(m.current.muted, "mute must survive a mode change")
        assertEquals(OperatorStatus.MUTED, m.current.status)
        m.unmute()
        assertEquals(OperatorStatus.ACTIVE, m.current.status)
    }

    @Test
    fun `mute is idempotent and emits exactly one event per transition`() = runTest {
        val m = manager()
        m.events.test {
            m.emergencyMute()
            m.emergencyMute()
            assertEquals(OperatorEvent.EmergencyMuteEngaged, awaitItem())
            m.unmute()
            m.unmute()
            assertEquals(OperatorEvent.MuteReleased, awaitItem())
            expectNoEvents()
        }
    }

    @Test
    fun `comment now is accepted when processing and rejected when muted or off`() = runTest {
        val m = manager(OperatorMode.STANDBY)
        m.events.test {
            assertTrue(m.commentNow())
            assertEquals(OperatorEvent.CommentNowRequested, awaitItem())

            m.emergencyMute()
            awaitItem() // mute engaged
            assertFalse(m.commentNow(), "muted Operator must not accept COMMENT NOW")
            m.unmute()
            awaitItem() // mute released

            m.off()
            awaitItem() // mode changed
            assertFalse(m.commentNow(), "OFF Operator must not accept COMMENT NOW")
            expectNoEvents()
        }
    }

    @Test
    fun `mode change emits event with from and to and no event when unchanged`() = runTest {
        val m = manager(OperatorMode.STANDBY)
        m.events.test {
            m.setMode(OperatorMode.WORK)
            assertEquals(OperatorEvent.ModeChanged(OperatorMode.STANDBY, OperatorMode.WORK), awaitItem())
            m.setMode(OperatorMode.WORK)
            expectNoEvents()
        }
    }

    @Test
    fun `cycle mode walks the selectable list and wraps`() {
        val m = manager(OperatorMode.STANDBY)
        val expected = OperatorMode.selectable.drop(1) + OperatorMode.selectable.first()
        expected.forEach { next ->
            m.cycleMode()
            assertEquals(next, m.current.mode)
        }
    }

    @Test
    fun `wit level is independent of mode`() {
        val m = manager(OperatorMode.WORK, WitLevel.DRY)
        m.setWit(WitLevel.UNHINGED)
        assertEquals(OperatorMode.WORK, m.current.mode)
        assertEquals(WitLevel.UNHINGED, m.current.wit)
    }

    @Test
    fun `subsystem updates are reflected and do not disturb other subsystems`() {
        val m = manager()
        m.updateSubsystem(Subsystem.MICROPHONE, SubsystemStatus(SubsystemState.READY, "Built-in mic"))
        assertEquals(SubsystemState.READY, m.current.subsystem(Subsystem.MICROPHONE).state)
        assertEquals("Built-in mic", m.current.subsystem(Subsystem.MICROPHONE).detail)
        assertEquals(SubsystemState.NOT_IMPLEMENTED, m.current.subsystem(Subsystem.GLASSES).state)
    }

    @Test
    fun `state flow emits distinct snapshots`() = runTest {
        val m = manager()
        m.state.test {
            assertEquals(OperatorMode.STANDBY, awaitItem().mode)
            m.activate()
            assertEquals(OperatorMode.ACTIVE, awaitItem().mode)
            m.activate() // no change → no emission
            expectNoEvents()
        }
    }
}
