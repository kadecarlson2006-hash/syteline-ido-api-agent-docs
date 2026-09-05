# CURRENT STATUS — OPERATOR

**Current milestone:** 2 — Bluetooth audio diagnostics (implemented; awaiting device verification of Milestones 1 and 2)

**Last updated:** 2026-09-05

## What works (verified)

- `:core` compiles and its 35 unit tests pass locally and in CI (state manager, decision model,
  config parsing, latency timeline, route selection, route event log, audio loopback state
  machine with fakes).
- `:app` compiles (`assembleDebug`) and its unit tests pass in GitHub Actions
  (`.github/workflows/operator-android.yml`). The debug APK is downloadable from the latest
  green run's artifacts (`operator-debug-apk`).

## What is implemented but NOT yet verified on a device

Milestone 1 (unchanged):
- Main screen, mode/wit selectors, ACTIVATE / STANDBY / COMMENT NOW / EMERGENCY MUTE.
- Microphone permission flow, RECORD TEST into memory, PLAY TEST with audio focus.

Milestone 2 (new):
- INPUT / OUTPUT chip selectors in the Audio Test panel listing every `AudioDeviceInfo`
  Android reports, plus DEFAULT (platform routing).
- Wired / USB / built-in selections use `setPreferredDevice`.
- Bluetooth SCO and BLE-headset selections raise the link with
  `AudioManager.setCommunicationDevice` (API 31+, the documented replacement for
  `startBluetoothSco`), record with `VOICE_COMMUNICATION`, play with
  `USAGE_VOICE_COMMUNICATION`, and clear the request afterwards. A2DP output is plain media.
- "Capture path" / "Playback path" lines say which audio source/usage was used and whether the
  preferred device or communication link was honoured; "actual" lines come from `routedDevice`.
- Bluetooth diagnostics panel: adapter state, paired devices (needs BLUETOOTH_CONNECT on
  API 31+), audio mode, communication-capable outputs, active communication device, and a full
  input/output device table with sample rates, channel counts, encodings, and addresses.
- Route event log: device added/removed, communication-device changes, what each capture and
  playback was actually routed to. Also mirrored to logcat (`AudioRouteMonitor`,
  `AndroidAudioRecorder`, `AndroidAudioPlayer`, `BluetoothStatusMonitor`).
- A selected device that disconnects falls back to DEFAULT automatically.

The Android module is written in a sandbox without an Android SDK, so `:app` compilation is
verified by GitHub Actions rather than locally. Nothing has been installed on a phone yet.

## What does not work / not started

- Everything from Milestone 3 onward: Meta glasses SDK, backend, memory, AI, TTS,
  transcription, rolling context, decision engine, BLE ring, camera, integrations.
- Explicit Bluetooth SCO selection on Android 10–11 (API 29–30): not supported by design; the
  UI says so. DEFAULT routing still works there.
- No launcher icon.

## Current blockers

- None beyond human verification on hardware.

## Next test (required before Milestone 3)

On a Samsung Galaxy phone, first the Milestone 1 checks (launch, GRANT MICROPHONE, RECORD TEST,
PLAY TEST, understandable speech, correct actual devices), then with a Bluetooth headset or the
Ray-Ban Meta glasses paired and connected:

1. GRANT BLUETOOTH → the paired list shows the headset/glasses with its name, flagged "audio".
2. The INPUT chips show a Bluetooth SCO (or BLE headset) entry; OUTPUT shows A2DP and SCO/BLE.
3. OUTPUT = A2DP entry → PLAY TEST → audio in the headset; "Output (actual)" = A2DP device.
4. INPUT = Bluetooth SCO entry → RECORD TEST → route log shows "Requested communication
   device…", then "Communication device active…", then "Capture routed to … (Bluetooth SCO)".
   Speak; PLAY TEST on the phone speaker should reproduce your voice from the headset mic.
5. OUTPUT = Bluetooth SCO entry → PLAY TEST → playback through the headset via the SCO link.
6. Disconnect the headset mid-selection → chips fall back to DEFAULT; log shows "Device removed".
7. Note the time between "Requested communication device" and "active" (SCO bring-up latency)
   and anything odd in audio quality (SCO is narrow/wide-band voice, not music quality).
