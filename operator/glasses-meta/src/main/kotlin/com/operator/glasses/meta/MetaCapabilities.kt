package com.operator.glasses.meta

import com.operator.core.glasses.CapabilityReport
import com.operator.core.glasses.Support

/**
 * What the Meta Wearables Device Access Toolkit 0.9.0 does and does not offer, as read from the
 * official SDK repository (README, CHANGELOG, AGENTS.md, samples) on 2026-09-05. Kept next to
 * the integration so the diagnostics screen and docs/META_GLASSES.md cannot drift apart.
 */
object MetaCapabilities {
    fun report(): List<CapabilityReport> = listOf(
        CapabilityReport("Device discovery & link state", Support.SUPPORTED, "Wearables.devices / devicesMetadata (name, type, linkState, compatibility)"),
        CapabilityReport("Registration via Meta AI app", Support.SUPPORTED, "Wearables.startRegistration(activity); Developer Mode needed for 0/0 attestation placeholders"),
        CapabilityReport("Device permissions", Support.SUPPORTED, "Wearables.checkPermissionStatus / RequestPermissionContract (CAMERA is the documented permission)"),
        CapabilityReport("Device session lifecycle", Support.SUPPORTED, "createSession(AutoDeviceSelector) → IDLE/STARTING/STARTED/PAUSED/STOPPING/STOPPED"),
        CapabilityReport("Camera video streaming", Support.SUPPORTED, "mwdat-camera: session.addCamera(StreamConfiguration) — wired in Milestone 16"),
        CapabilityReport("Photo capture", Support.SUPPORTED, "Camera.stream.capturePhoto — wired in Milestone 16"),
        CapabilityReport("Thermal / battery state", Support.SUPPORTED, "Wearables.getDeviceState(id).thermalLevel and DeviceSessionError battery/thermal cases — not wired yet"),
        CapabilityReport("Display rendering", Support.UNSUPPORTED, "mwdat-display targets Meta Ray-Ban Display only; Ray-Ban Meta Gen 2 has no display"),
        CapabilityReport("Microphone access via SDK", Support.UNSUPPORTED, "No audio-capture API in DAT 0.9.0; Meta's own sample records sound from the PHONE mic"),
        CapabilityReport("Speaker output via SDK", Support.UNSUPPORTED, "No audio-playback API; use the Bluetooth A2DP/SCO route (Milestone 2/10)"),
        CapabilityReport("Continuous glasses microphone", Support.UNKNOWN, "Only via Bluetooth HFP/SCO as a normal headset — Milestone 10 measures reliability"),
        CapabilityReport("Captouch / button as app input", Support.UNSUPPORTED, "Captouch tap/hold pauses, resumes, or stops the SDK camera stream; not delivered to apps as events"),
        CapabilityReport("Meta AI coexistence", Support.UNKNOWN, "Docs: sessions pause when 'another experience takes over the device' — measure in Milestone 10"),
        CapabilityReport("Background operation", Support.SUPPORTED, "Sample keeps streaming alive with a connectedDevice foreground service"),
        CapabilityReport("Testing without hardware", Support.SUPPORTED, "MockDeviceKit: pairGlasses(RAYBAN_META), powerOn/unfold/don, captouch simulation"),
    )
}
