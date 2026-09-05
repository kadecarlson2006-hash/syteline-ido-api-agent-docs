package com.operator.core.diagnostics

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RouteEventLogTest {
    @Test
    fun `keeps only the most recent events`() {
        var now = 0L
        val log = RouteEventLog(capacity = 3, clock = { ++now })
        listOf("a", "b", "c", "d").forEach(log::log)
        assertEquals(listOf("b", "c", "d"), log.events.value.map { it.message })
        assertEquals(listOf(2L, 3L, 4L), log.events.value.map { it.atMillis })
        log.clear()
        assertTrue(log.events.value.isEmpty())
    }
}
