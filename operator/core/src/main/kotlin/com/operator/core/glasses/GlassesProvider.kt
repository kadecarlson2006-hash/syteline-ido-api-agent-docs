package com.operator.core.glasses

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Isolation boundary for smart-glasses vendor SDKs (ADR-004). The rest of Operator only sees
 * this interface; the Meta Wearables Device Access Toolkit lives behind it in `:glasses-meta`.
 *
 * Audio is deliberately NOT part of this contract: the Meta toolkit (0.9.0) exposes no
 * microphone or speaker API, so glasses audio flows through the standard Bluetooth routes
 * handled by the audio subsystem.
 */
interface GlassesProvider {
    val state: StateFlow<GlassesState>

    /** Idempotent. Initialises the vendor SDK and starts observing registration/devices. */
    fun initialize()

    /** Creates and starts a device session with the auto-selected device (no-op without SDK). */
    fun startSession()

    fun stopSession()

    /** Developer/diagnostic actions offered by this provider, rendered generically by the UI. */
    val actions: List<GlassesAction>
}

/** Used when no vendor SDK is compiled in. Reports everything honestly as absent. */
class NoGlassesProvider(reason: String = "No glasses SDK in this build") : GlassesProvider {
    private val _state = MutableStateFlow(
        GlassesState(
            providerName = "none",
            sdkPresent = false,
            capabilities = listOf(
                CapabilityReport("Vendor SDK", Support.UNSUPPORTED, reason),
                CapabilityReport("Glasses audio via Bluetooth", Support.UNKNOWN, "Standard HFP/A2DP path — see Milestone 2 diagnostics"),
            ),
        ),
    )
    override val state: StateFlow<GlassesState> = _state
    override fun initialize() = Unit
    override fun startSession() = Unit
    override fun stopSession() = Unit
    override val actions: List<GlassesAction> = emptyList()
}
