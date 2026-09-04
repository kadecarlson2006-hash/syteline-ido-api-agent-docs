package com.operator.app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.operator.app.BuildConfig
import com.operator.app.di.OperatorContainer
import com.operator.core.model.OperatorMode
import com.operator.core.model.WitLevel
import com.operator.core.state.OperatorEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class OperatorViewModel(private val container: OperatorContainer) : ViewModel() {

    private val lastEvent = MutableStateFlow<String?>(null)

    val uiState: StateFlow<OperatorUiState> = combine(
        container.stateManager.state,
        container.loopback.state,
        container.audioRouteMonitor.routes,
        container.microphonePermission.granted,
        lastEvent,
    ) { operator, loopback, routes, permission, event ->
        OperatorUiState(
            operator = operator,
            loopback = loopback,
            routes = routes,
            microphonePermissionGranted = permission,
            lastEvent = event,
            config = container.config,
            appVersion = BuildConfig.VERSION_NAME,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), OperatorUiState(config = container.config))

    init {
        viewModelScope.launch {
            container.stateManager.events.collect { event ->
                lastEvent.value = when (event) {
                    OperatorEvent.CommentNowRequested -> "COMMENT NOW received (decision engine arrives in Milestone 12)"
                    OperatorEvent.EmergencyMuteEngaged -> "EMERGENCY MUTE engaged"
                    OperatorEvent.MuteReleased -> "Mute released"
                    is OperatorEvent.ModeChanged -> "Mode ${event.from.label} → ${event.to.label}"
                }
                if (event == OperatorEvent.EmergencyMuteEngaged) container.loopback.cancel()
            }
        }
    }

    // --- Operator controls ---
    fun activate() = container.stateManager.activate()
    fun standby() = container.stateManager.standby()
    fun commentNow() {
        if (!container.stateManager.commentNow()) lastEvent.value = "COMMENT NOW ignored (muted or OFF)"
    }
    fun toggleMute() = container.stateManager.toggleMute()
    fun setMode(mode: OperatorMode) = container.stateManager.setMode(mode)
    fun setWit(wit: WitLevel) = container.stateManager.setWit(wit)

    // --- Milestone 1 audio test ---
    fun refreshPermission() {
        container.microphonePermission.refresh()
        container.audioRouteMonitor.refresh()
    }
    fun recordTest() {
        if (!container.loopback.startRecordTest()) lastEvent.value = "RECORD TEST ignored (busy)"
    }
    fun playTest() {
        if (!container.loopback.startPlayTest()) lastEvent.value = "PLAY TEST ignored (busy or nothing recorded)"
    }
    fun stopAudio() = container.loopback.cancel()
    fun discardClip() = container.loopback.discardClip()

    companion object {
        fun factory(container: OperatorContainer): ViewModelProvider.Factory = viewModelFactory {
            initializer { OperatorViewModel(container) }
        }
    }
}
