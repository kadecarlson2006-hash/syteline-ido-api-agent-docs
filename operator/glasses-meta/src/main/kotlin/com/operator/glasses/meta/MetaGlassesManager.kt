package com.operator.glasses.meta

import android.app.Activity
import android.content.Context
import android.util.Log
import com.meta.wearable.dat.core.Wearables
import com.meta.wearable.dat.core.selectors.AutoDeviceSelector
import com.meta.wearable.dat.core.session.DeviceSession
import com.meta.wearable.dat.core.types.Device
import com.meta.wearable.dat.core.types.DeviceIdentifier
import com.meta.wearable.dat.core.types.Permission
import com.meta.wearable.dat.mockdevice.MockDeviceKit
import com.meta.wearable.dat.mockdevice.api.GlassesModel
import com.meta.wearable.dat.mockdevice.api.MockGlasses
import com.operator.core.glasses.GlassesAction
import com.operator.core.glasses.GlassesDevice
import com.operator.core.glasses.GlassesProvider
import com.operator.core.glasses.GlassesRegistration
import com.operator.core.glasses.GlassesSessionState
import com.operator.core.glasses.GlassesState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * The single place Operator talks to the Meta Wearables Device Access Toolkit (ADR-004).
 *
 * Follows the patterns of Meta's own samples (CameraAccess `WearablesViewModel`, DisplayAccess
 * `WearablesRepository`): initialise once, observe registration / devices / metadata, create a
 * `DeviceSession` with an `AutoDeviceSelector`, surface typed `DatResult` failures instead of
 * throwing. Milestone 3 wires connection, device state, permissions, and MockDeviceKit; camera
 * streaming (Milestone 16) will attach `mwdat-camera` to the same session.
 */
class MetaGlassesManager(
    private val context: Context,
    private val scope: CoroutineScope,
    developerModeBuild: Boolean,
) : GlassesProvider {

    private val _state = MutableStateFlow(
        GlassesState(
            providerName = "Meta Wearables Device Access Toolkit",
            sdkPresent = true,
            sdkVersion = BuildConfig.MWDAT_VERSION,
            developerModeBuild = developerModeBuild,
            capabilities = MetaCapabilities.report(),
        ),
    )
    override val state: StateFlow<GlassesState> = _state.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, t ->
        Log.e(TAG, "Wearables monitoring failed", t)
        setError("SDK monitoring failed: ${t.message ?: t::class.simpleName}")
    }

    private val selector by lazy { AutoDeviceSelector() }
    private val metadataJobs = mutableMapOf<DeviceIdentifier, Job>()
    private val devicesById = linkedMapOf<String, GlassesDevice>()

    private var session: DeviceSession? = null
    private var sessionJobs = mutableListOf<Job>()

    private val mockKit by lazy { MockDeviceKit.getInstance(context.applicationContext) }
    private val mockGlasses = mutableListOf<MockGlasses>()

    @Volatile private var initialized = false

    // ---------------------------------------------------------------- lifecycle

    override fun initialize() {
        if (initialized) return
        var ok = true
        Wearables.initialize(context.applicationContext).onFailure { error, _ ->
            ok = false
            setError("Wearables.initialize failed: ${error.description}")
        }
        if (!ok) return
        initialized = true
        _state.update { it.copy(initialized = true, lastError = null) }
        startMonitoring()
    }

    private fun startMonitoring() {
        scope.launch(exceptionHandler) {
            Wearables.registrationState.collect { value ->
                _state.update { it.copy(registration = mapRegistration(value.name)) }
            }
        }
        scope.launch(exceptionHandler) {
            Wearables.registrationErrorStream.collect { error ->
                setError("Registration: ${error.description}")
            }
        }
        scope.launch(exceptionHandler) {
            Wearables.devices.collect { identifiers -> onDevices(identifiers) }
        }
        scope.launch(exceptionHandler) {
            selector.activeDeviceFlow().collect { id ->
                _state.update { it.copy(activeDeviceId = id?.toString()) }
            }
        }
    }

    private fun onDevices(identifiers: Set<DeviceIdentifier>) {
        val removed = metadataJobs.keys - identifiers
        removed.forEach { id ->
            metadataJobs.remove(id)?.cancel()
            devicesById.remove(id.toString())
        }
        val added = identifiers - metadataJobs.keys
        added.forEach { id ->
            devicesById.putIfAbsent(id.toString(), GlassesDevice(id.toString(), id.toString(), "?", "?", "?", null))
            metadataJobs[id] = scope.launch(exceptionHandler) {
                Wearables.devicesMetadata[id]?.collect { device -> onMetadata(id, device) }
            }
        }
        publishDevices()
    }

    private fun onMetadata(id: DeviceIdentifier, device: Device) {
        devicesById[id.toString()] = GlassesDevice(
            id = id.toString(),
            name = device.name.ifEmpty { id.toString() },
            type = device.deviceType.toString(),
            linkState = device.linkState.toString(),
            compatibility = device.compatibility.toString(),
            isDisplayCapable = runCatching { device.isDisplayCapable() }.getOrNull(),
        )
        publishDevices()
    }

    private fun publishDevices() = _state.update { it.copy(devices = devicesById.values.toList()) }

    override fun startSession() {
        if (!initialized) { setError("Initialise the SDK first"); return }
        if (session != null) return
        Wearables.createSession(selector)
            .onSuccess { created ->
                session = created
                observeSession(created)
                _state.update { it.copy(session = GlassesSessionState.STARTING, lastError = null) }
                created.start()
            }
            .onFailure { error, _ -> setError("createSession failed: ${error.description}") }
    }

    private fun observeSession(s: DeviceSession) {
        sessionJobs += scope.launch(exceptionHandler) {
            s.state.collect { value ->
                val mapped = mapSession(value.name)
                _state.update { it.copy(session = mapped) }
                if (mapped == GlassesSessionState.STOPPED) cleanupSession()
            }
        }
        sessionJobs += scope.launch(exceptionHandler) {
            s.errors.collect { error -> setError("Session: ${error.description}") }
        }
    }

    override fun stopSession() {
        val current = session ?: return
        _state.update { it.copy(session = GlassesSessionState.STOPPING) }
        current.stop()
    }

    private fun cleanupSession() {
        sessionJobs.forEach { it.cancel() }
        sessionJobs.clear()
        session = null
        _state.update { it.copy(session = GlassesSessionState.STOPPED) }
    }

    private fun checkCameraPermission() {
        scope.launch(exceptionHandler) {
            Wearables.checkPermissionStatus(Permission.CAMERA)
                .onSuccess { status -> _state.update { it.copy(cameraPermission = status.toString()) } }
                .onFailure { error, _ -> setError("checkPermissionStatus: ${error.description}") }
        }
    }

    // ---------------------------------------------------------------- developer actions

    override val actions: List<GlassesAction>
        get() = listOf(
            GlassesAction("INITIALISE SDK") { initialize() },
            GlassesAction("REGISTER WITH META AI", needsActivity = true) { host ->
                activityOrError(host)?.let { Wearables.startRegistration(it) }
            },
            GlassesAction("UNREGISTER", needsActivity = true) { host ->
                activityOrError(host)?.let { Wearables.startUnregistration(it) }
            },
            GlassesAction("OPEN FIRMWARE UPDATE", needsActivity = true) { host ->
                activityOrError(host)?.let { activity ->
                    Wearables.openFirmwareUpdate(activity).onFailure { error, _ -> setError("openFirmwareUpdate: ${error.description}") }
                }
            },
            GlassesAction("START SESSION") { startSession() },
            GlassesAction("STOP SESSION") { stopSession() },
            GlassesAction("CHECK CAMERA PERMISSION") { checkCameraPermission() },
            GlassesAction("MOCK: ENABLE KIT") { enableMock() },
            GlassesAction("MOCK: PAIR RAY-BAN META") { pairMockGlasses() },
            GlassesAction("MOCK: POWER ON + UNFOLD + DON") { wearMockGlasses() },
            GlassesAction("MOCK: CAPTOUCH TAP") { mockTap() },
            GlassesAction("MOCK: DISABLE KIT") { disableMock() },
        )

    private fun activityOrError(host: Any?): Activity? =
        (host as? Activity).also { if (it == null) setError("This action needs the Activity") }

    private fun enableMock() {
        mockKit.enable()
        _state.update { it.copy(mockEnabled = true, lastError = null) }
        if (!initialized) initialize()
    }

    private fun disableMock() {
        mockKit.disable()
        mockGlasses.clear()
        _state.update { it.copy(mockEnabled = false, mockDeviceCount = 0) }
    }

    private fun pairMockGlasses() {
        if (!_state.value.mockEnabled) enableMock()
        scope.launch(exceptionHandler) {
            mockKit.pairGlasses(GlassesModel.RAYBAN_META).fold(
                onSuccess = { glasses ->
                    mockGlasses += glasses
                    _state.update { it.copy(mockDeviceCount = mockGlasses.size, lastError = null) }
                },
                onFailure = { error, _ -> setError("MockDeviceKit.pairGlasses: $error") },
            )
        }
    }

    private fun wearMockGlasses() {
        val glasses = mockGlasses.lastOrNull() ?: run { setError("Pair mock glasses first"); return }
        scope.launch(exceptionHandler) {
            glasses.powerOn()
            glasses.unfold()
            glasses.don()
        }
    }

    private fun mockTap() {
        val glasses = mockGlasses.lastOrNull() ?: run { setError("Pair mock glasses first"); return }
        scope.launch(exceptionHandler) { glasses.services.captouch.tap() }
    }

    // ---------------------------------------------------------------- helpers

    private fun setError(message: String) {
        Log.w(TAG, message)
        _state.update { it.copy(lastError = message) }
    }

    private fun mapRegistration(name: String): GlassesRegistration =
        GlassesRegistration.entries.firstOrNull { it.name.equals(name, ignoreCase = true) } ?: GlassesRegistration.UNKNOWN

    private fun mapSession(name: String): GlassesSessionState =
        GlassesSessionState.entries.firstOrNull { it.name.equals(name, ignoreCase = true) } ?: GlassesSessionState.NONE

    private companion object {
        const val TAG = "MetaGlassesManager"
    }
}
