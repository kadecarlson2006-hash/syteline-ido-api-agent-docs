package com.operator.core.glasses

import com.operator.core.model.SubsystemState
import com.operator.core.model.SubsystemStatus

/** Honest capability classification. Nothing is SUPPORTED until documentation or a test says so. */
enum class Support(val label: String) { SUPPORTED("SUPPORTED"), UNSUPPORTED("UNSUPPORTED"), UNKNOWN("UNKNOWN") }

data class CapabilityReport(val capability: String, val support: Support, val note: String)

/** Mirrors the toolkit's registration states plus UNKNOWN for "no SDK / not initialised". */
enum class GlassesRegistration { UNKNOWN, UNAVAILABLE, AVAILABLE, REGISTERING, REGISTERED, UNREGISTERING }

/** Mirrors the toolkit's device-session states plus NONE for "no session object". */
enum class GlassesSessionState { NONE, IDLE, STARTING, STARTED, PAUSED, STOPPING, STOPPED }

data class GlassesDevice(
    val id: String,
    val name: String,
    val type: String,
    val linkState: String,
    val compatibility: String,
    val isDisplayCapable: Boolean?,
) {
    val isConnected: Boolean get() = linkState.equals("CONNECTED", ignoreCase = true)
    val summary: String get() = "$name · $type · $linkState"
}

/**
 * A developer/diagnostic action the provider offers (register, start session, pair mock glasses…).
 * [run] receives the host Activity (as an opaque object, to keep core Android-free) when
 * [needsActivity] is true; otherwise null.
 */
data class GlassesAction(
    val label: String,
    val needsActivity: Boolean = false,
    val enabled: Boolean = true,
    val run: (host: Any?) -> Unit,
)

data class GlassesState(
    val providerName: String,
    val sdkPresent: Boolean,
    val sdkVersion: String? = null,
    val initialized: Boolean = false,
    val developerModeBuild: Boolean? = null,
    val registration: GlassesRegistration = GlassesRegistration.UNKNOWN,
    val devices: List<GlassesDevice> = emptyList(),
    val activeDeviceId: String? = null,
    val session: GlassesSessionState = GlassesSessionState.NONE,
    val cameraPermission: String? = null,
    val mockEnabled: Boolean = false,
    val mockDeviceCount: Int = 0,
    val lastError: String? = null,
    val capabilities: List<CapabilityReport> = emptyList(),
) {
    val connectedDevices: List<GlassesDevice> get() = devices.filter { it.isConnected }

    /** What the main-screen GLASSES indicator should show for this state. */
    fun toSubsystemStatus(): SubsystemStatus = when {
        !sdkPresent -> SubsystemStatus(SubsystemState.NOT_CONFIGURED, "Meta SDK not compiled in")
        lastError != null && session == GlassesSessionState.NONE && devices.isEmpty() ->
            SubsystemStatus(SubsystemState.ERROR, lastError)
        !initialized -> SubsystemStatus(SubsystemState.NOT_CONFIGURED, "SDK not initialised")
        registration != GlassesRegistration.REGISTERED ->
            SubsystemStatus(SubsystemState.NOT_CONFIGURED, "Registration: ${registration.name}")
        session == GlassesSessionState.STARTED ->
            SubsystemStatus(SubsystemState.ACTIVE, connectedDevices.firstOrNull()?.name ?: "Session started")
        connectedDevices.isNotEmpty() ->
            SubsystemStatus(SubsystemState.READY, connectedDevices.joinToString { it.name })
        devices.isNotEmpty() ->
            SubsystemStatus(SubsystemState.UNAVAILABLE, devices.joinToString { it.summary })
        else -> SubsystemStatus(SubsystemState.UNAVAILABLE, "No glasses linked")
    }
}
