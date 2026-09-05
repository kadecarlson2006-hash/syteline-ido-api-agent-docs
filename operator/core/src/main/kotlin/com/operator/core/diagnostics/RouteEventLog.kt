package com.operator.core.diagnostics

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class RouteEvent(val atMillis: Long, val message: String)

/**
 * Bounded, in-memory log of audio routing events (device added/removed, what Android actually
 * routed a capture or playback to, communication-device changes). Shown in the diagnostics
 * panel and mirrored to logcat by the Android layer. Never persisted.
 */
class RouteEventLog(
    private val capacity: Int = 60,
    private val clock: () -> Long = System::currentTimeMillis,
) {
    init {
        require(capacity > 0) { "capacity must be > 0" }
    }

    private val _events = MutableStateFlow<List<RouteEvent>>(emptyList())
    val events: StateFlow<List<RouteEvent>> = _events.asStateFlow()

    fun log(message: String) {
        val event = RouteEvent(clock(), message)
        _events.update { current -> (current + event).takeLast(capacity) }
    }

    fun clear() = _events.update { emptyList() }
}
