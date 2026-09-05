package com.operator.app.glasses

import com.operator.core.glasses.GlassesProvider
import com.operator.core.model.Subsystem
import com.operator.core.state.OperatorStateManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/** Keeps the GLASSES indicator on the main screen in step with the provider state. */
class GlassesSubsystemReporter(
    private val stateManager: OperatorStateManager,
    private val provider: GlassesProvider,
) {
    fun start(scope: CoroutineScope) {
        provider.state
            .onEach { stateManager.updateSubsystem(Subsystem.GLASSES, it.toSubsystemStatus()) }
            .launchIn(scope)
    }
}
