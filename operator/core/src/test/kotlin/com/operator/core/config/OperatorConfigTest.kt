package com.operator.core.config

import com.operator.core.model.OperatorMode
import com.operator.core.model.WitLevel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class OperatorConfigTest {

    @Test
    fun `defaults are sensible and secret-free`() {
        val c = OperatorConfig()
        assertEquals(OperatorMode.STANDBY, c.defaultMode)
        assertEquals(WitLevel.NORMAL, c.defaultWit)
        assertNull(c.fastModelId)
        assertNull(c.elevenLabsVoiceId)
    }

    @Test
    fun `fromMap parses known keys case-insensitively and ignores unknown ones`() {
        val c = OperatorConfig.fromMap(
            mapOf(
                OperatorConfig.Keys.DEFAULT_MODE to "work",
                OperatorConfig.Keys.DEFAULT_WIT to "SHARP",
                OperatorConfig.Keys.FAST_MODEL_ID to "  some/model  ",
                OperatorConfig.Keys.ROLLING_CONTEXT_SECONDS to "120",
                OperatorConfig.Keys.RECORD_TEST_DURATION_MILLIS to "5000",
                "SOMETHING_ELSE" to "x",
            ),
        )
        assertEquals(OperatorMode.WORK, c.defaultMode)
        assertEquals(WitLevel.SHARP, c.defaultWit)
        assertEquals("some/model", c.fastModelId)
        assertEquals(120, c.rollingContextSeconds)
        assertEquals(5_000, c.recordTestDurationMillis)
    }

    @Test
    fun `malformed or empty values fall back to defaults`() {
        val c = OperatorConfig.fromMap(
            mapOf(
                OperatorConfig.Keys.DEFAULT_MODE to "banana",
                OperatorConfig.Keys.ROLLING_CONTEXT_SECONDS to "lots",
                OperatorConfig.Keys.FAST_MODEL_ID to "",
            ),
        )
        assertEquals(OperatorMode.STANDBY, c.defaultMode)
        assertEquals(60, c.rollingContextSeconds)
        assertNull(c.fastModelId)
    }

    @Test
    fun `record test duration is bounded`() {
        assertFailsWith<IllegalArgumentException> { OperatorConfig(recordTestDurationMillis = 100) }
        assertFailsWith<IllegalArgumentException> { OperatorConfig(recordTestDurationMillis = 60_000) }
    }
}
