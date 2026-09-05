# Meta Ray-Ban glasses — what the toolkit supports (Milestone 3)

Source of truth: the official SDK repository `facebook/meta-wearables-dat-android`, release
**0.9.0 (2026-08-03)** — README, CHANGELOG, AGENTS.md, the bundled skills, and the
CameraAccess / DisplayAccess samples — read on 2026-09-05. The hosted docs
(`wearables.developer.meta.com`) were not reachable from the authoring environment; anything
that only they could confirm is marked UNKNOWN. The toolkit is a **developer preview**.

## Verdict for Operator

| Question from the project brief | Answer (DAT 0.9.0) |
|---|---|
| 1. Device discovery | SUPPORTED — `Wearables.devices` + `devicesMetadata` (name, type, link state, compatibility) |
| 2. Connection status | SUPPORTED — `LinkState` per device; `DeviceSession` state machine |
| 3. Microphone access | **UNSUPPORTED via SDK** — no audio-capture API. Meta's own sample records "sound-in-video" from the *phone* microphone (`AudioInputHandler`, `AudioSource.MIC`). Glasses mic only as a Bluetooth HFP/SCO headset (Milestone 2 path). |
| 4. Speaker routing | **UNSUPPORTED via SDK** — no playback API. Use Bluetooth A2DP (media) / SCO (voice) routing. |
| 5. Camera access | SUPPORTED — `mwdat-camera`: video stream (HEVC, quality/frame-rate config) and photo capture. Requires the CAMERA device permission granted through the Meta AI app. Wired in Milestone 16. |
| 6. Gestures / buttons | **UNSUPPORTED as app input** — captouch tap / tap-and-hold pause, resume, or stop the SDK camera stream; nothing is delivered to the app as an event. Display taps exist only on Ray-Ban Display. BLE ring remains the plan (Milestone 15). |
| 7. Developer mode | SUPPORTED — enable Developer Mode in the Meta AI app; build with attestation placeholders `0`/`0`. Resets after app/firmware updates; per linked device. |
| 8. Background audio behaviour | UNKNOWN — the SDK has no audio; Bluetooth audio in background is an Android question (foreground service with `microphone` type), Milestone 10/11. |
| 9. Audio focus conflicts | UNKNOWN — measure with Meta AI and music playing (Milestone 10). |
| 10. Interaction with Meta AI | UNKNOWN — docs say a session is paused when "another experience takes over the device". |
| 11. Interaction with music | UNKNOWN — A2DP is shared; Operator playback takes transient focus (Milestone 1 player). |
| 12. Interaction with phone calls | UNKNOWN — SCO is the call link; expect Operator capture to be pre-empted. |
| 13. Continuous microphone | UNSUPPORTED via SDK; UNKNOWN via Bluetooth SCO (reliability, quality, battery) — Milestone 10. |
| 14. Battery considerations | PARTIAL — SDK reports thermal level and battery/thermal session errors; real numbers come from Milestone 10 measurements. |

The product consequence: **Operator's audio path does not depend on the Meta SDK at all.**
Phone mic (or glasses mic over HFP) → Operator → glasses speakers over Bluetooth is the design,
exactly as the "hardware fallback" section of the brief anticipated. The SDK adds camera,
device state, and registration.

## Integration facts

- Artifacts: `com.meta.wearable:mwdat-core|mwdat-camera|mwdat-display|mwdat-mockdevice:0.9.0`
  on **GitHub Packages** (`maven.pkg.github.com/facebook/meta-wearables-dat-android`). A token
  with `read:packages` is required even though the packages are public. Not on Maven Central.
- Meta's samples use `minSdk 31`; Operator now requires Android 12 (ADR-014).
- Manifest: `BLUETOOTH`, `BLUETOOTH_CONNECT`, `INTERNET`; `<meta-data>` `APPLICATION_ID` and
  `CLIENT_TOKEN` (placeholders `0` in Developer Mode); a `VIEW/BROWSABLE` intent filter with the
  app's URL scheme for the registration return trip (`operator`). Whether the scheme string must
  be pre-registered anywhere is UNKNOWN.
- Privacy: `ANALYTICS_OPT_OUT=true` and `CRASH_REPORTING_OPT_OUT=true` are set (ADR-015).
- Flow: `Wearables.initialize(context)` once → `startRegistration(activity)` (opens Meta AI)
  → observe `registrationState` / `devices` → `createSession(AutoDeviceSelector())` →
  `session.start()` → capabilities attach to the session. All results are typed `DatResult`s.
- Supported glasses models in the mock kit enum: `RAYBAN_META`, `OAKLEY_META_HSTN`,
  `OAKLEY_META_VANGUARD`, `RAYBAN_META_OPTICS`, `META_GLASSES`. Which model string the Gen 2
  frames report is UNKNOWN until seen on the device table.
- Testing without hardware: `MockDeviceKit` (enable, `pairGlasses`, power/fold/don, captouch,
  mock camera feeds). Operator exposes these as developer actions on the Glasses panel.

## What Operator wires in Milestone 3

`:glasses-meta` → `MetaGlassesManager` implements `core.glasses.GlassesProvider`: initialise,
registration state + errors, device list with metadata, active device, session start/stop with
state and errors, camera-permission check, firmware-update deep link, MockDeviceKit actions.
The Glasses panel shows all of it plus this capability table; the GLASSES subsystem indicator
follows `GlassesState.toSubsystemStatus()`.

Not wired yet: camera stream/photo (Milestone 16), thermal state flow, display (not applicable).

## Device test plan (needs the glasses + Meta AI app with Developer Mode)

1. Build with a `github_token` in `local.properties`; Glasses panel shows "SDK present · v0.9.0".
2. Initialised = yes at launch; Registration = AVAILABLE (Meta AI installed) or UNAVAILABLE.
3. REGISTER WITH META AI → Meta AI opens → return → Registration = REGISTERED.
4. Linked devices lists the glasses with type, CONNECTED, and compatibility.
5. START SESSION → STARTING → STARTED; STOP SESSION → STOPPED. Note any session errors.
6. CHECK CAMERA PERMISSION → NotDetermined/Denied/Granted as reported.
7. Without glasses: MOCK: ENABLE KIT → PAIR → POWER ON + UNFOLD + DON → device appears
   CONNECTED → START SESSION works against the mock.
8. Record the glasses' reported type string and firmware/compatibility for the risk log.
