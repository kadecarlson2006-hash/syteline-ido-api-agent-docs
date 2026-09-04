package com.operator.core.state

import com.operator.core.model.OperatorMode
import com.operator.core.model.OperatorState
import com.operator.core.model.Subsystem
import com.operator.core.model.SubsystemStatus
import com.operator.core.model.WitLevel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Single source of truth for [OperatorState].
 *
 * Pure Kotlin, no Android. All mutations go through the named intent methods below so that
 * the rules (e.g. "mute survives mode changes", "COMMENT NOW is ignored while muted or OFF")
 * live in exactly one place and are unit-tested.
 *
 * Thread-safety: backed by [MutableStateFlow.update] (CAS loop), safe to call from any thread.
 */
class OperatorStateManager(
    initialMode: OperatorMode,
    initialWit: WitLevel,
) {
    private val _state = MutableStateFlow(OperatorState(mode = initialMode, wit = initialWit))
    val state: StateFlow<OperatorState> = _state.asStateFlow()

    private val _events = MutableSharedFlow<OperatorEvent>(
        extraBufferCapacity = 16,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val events: SharedFlow<OperatorEvent> = _events.asSharedFlow()

    val current: OperatorState get() = _state.value

    /** "ACTIVATE OPERATOR" — enters ACTIVE (does not clear an emergency mute). */
    fun activate() = setMode(OperatorMode.ACTIVE)

    /** "STANDBY" — enters STANDBY. */
    fun standby() = setMode(OperatorMode.STANDBY)

    /** Turns all processing off. */
    fun off() = setMode(OperatorMode.OFF)

    fun setMode(mode: OperatorMode) {
        var previous: OperatorMode? = null
        _state.update { s ->
            previous = s.mode
            if (s.mode == mode) s else s.copy(mode = mode)
        }
        val from = previous
        if (from != null && from != mode) _events.tryEmit(OperatorEvent.ModeChanged(from, mode))
    }

    /** Cycles through the selectable modes (STANDBY → ACTIVE → WORK → SOCIAL → QUIET → CHAOS → STANDBY). */
    fun cycleMode() {
        val list = OperatorMode.selectable
        val idx = list.indexOf(current.mode)
        setMode(list[(idx + 1).mod(list.size)])
    }

    fun setWit(wit: WitLevel) {
        _state.update { s -> if (s.wit == wit) s else s.copy(wit = wit) }
    }

    /** EMERGENCY MUTE. Idempotent. Emits [OperatorEvent.EmergencyMuteEngaged] on the transition. */
    fun emergencyMute() {
        var changed = false
        _state.update { s ->
            if (s.muted) s else { changed = true; s.copy(muted = true) }
        }
        if (changed) _events.tryEmit(OperatorEvent.EmergencyMuteEngaged)
    }

    fun unmute() {
        var changed = false
        _state.update { s ->
            if (!s.muted) s else { changed = true; s.copy(muted = false) }
        }
        if (changed) _events.tryEmit(OperatorEvent.MuteReleased)
    }

    fun toggleMute() = if (current.muted) unmute() else emergencyMute()

    /**
     * COMMENT NOW. Returns true if the request was accepted and an event emitted.
     * Ignored (returns false) when muted or OFF — a muted Operator must stay silent.
     */
    fun commentNow(): Boolean {
        val s = current
        if (s.muted || !s.isProcessing) return false
        return _events.tryEmit(OperatorEvent.CommentNowRequested)
    }

    fun updateSubsystem(subsystem: Subsystem, status: SubsystemStatus) {
        _state.update { s ->
            if (s.subsystems[subsystem] == status) s
            else s.copy(subsystems = s.subsystems + (subsystem to status))
        }
    }
}
