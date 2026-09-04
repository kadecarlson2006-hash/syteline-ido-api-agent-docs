package com.operator.core.decision

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNull

class ResponseDecisionTest {

    @Test
    fun `silence is a valid decision with no response text`() {
        val d = ResponseDecision.silence()
        assertFalse(d.shouldSpeak)
        assertEquals(ResponseCategory.NO_RESPONSE, d.category)
        assertNull(d.response)
    }

    @Test
    fun `speaking requires response text`() {
        assertFailsWith<IllegalArgumentException> {
            ResponseDecision(
                shouldSpeak = true, category = ResponseCategory.HUMOR,
                confidence = 0.9f, urgency = 0.2f, relevance = 0.8f,
                response = "  ", reasonCode = "GOOD_HUMOR_TIMING",
            )
        }
    }

    @Test
    fun `scores must be within 0 and 1`() {
        assertFailsWith<IllegalArgumentException> {
            ResponseDecision(false, ResponseCategory.NO_RESPONSE, 1.5f, 0f, 0f, null, null)
        }
    }

    @Test
    fun `suppression keeps the category but forces silence with a reason`() {
        val d = ResponseDecision(
            shouldSpeak = true, category = ResponseCategory.HUMOR,
            confidence = 0.9f, urgency = 0.1f, relevance = 0.9f,
            response = "A bold strategy, sir.", reasonCode = "GOOD_HUMOR_TIMING",
        ).suppressed("RECENTLY_SPOKE")
        assertFalse(d.shouldSpeak)
        assertEquals(ResponseCategory.HUMOR, d.category)
        assertEquals("RECENTLY_SPOKE", d.reasonCode)
    }

    @Test
    fun `silent engine never speaks`() = runTest {
        val d = SilentDecisionEngine.decide(DecisionRequest(DecisionTrigger.COMMENT_NOW, "Who played Deckard?"))
        assertFalse(d.shouldSpeak)
        assertEquals("ENGINE_NOT_IMPLEMENTED", d.reasonCode)
    }
}
