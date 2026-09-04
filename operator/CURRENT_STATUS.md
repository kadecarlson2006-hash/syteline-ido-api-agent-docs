# CURRENT STATUS — OPERATOR

**Current milestone:** 1 — Phone audio loopback (Milestones 0 and 1 implemented, awaiting device verification)

**Last updated:** 2026-09-04

## What works (verified)

- `:core` compiles and its unit tests pass (state manager, decision model, config parsing,
  latency timeline, audio loopback state machine with fakes).
- Project structure, version catalog, Gradle wrapper (9.5.0).

## What is implemented but NOT yet verified on a device

- Main screen: OPERATOR / PRIVATE ASSISTANCE SYSTEM / STATUS, seven subsystem indicators,
  ACTIVATE / STANDBY / COMMENT NOW / EMERGENCY MUTE, mode selector, wit selector.
- Microphone permission request flow.
- RECORD TEST (4 s default, configurable) into memory with progress bar.
- PLAY TEST through the current default Android output with audio focus.
- Display of available input/output devices and the device Android actually routed to.
- Basic diagnostics panel.

The Android module was written against the documented AGP 9 / Compose APIs but the
development sandbox used to write it has no Android SDK (dl.google.com is blocked there), so
compilation of `:app` is verified by the GitHub Actions workflow
`.github/workflows/operator-android.yml`, not locally. Check the latest run before installing.

## What does not work / not started

- Everything from Milestone 2 onward: Bluetooth diagnostics, Meta glasses, backend, memory,
  AI, TTS, transcription, rolling context, decision engine, BLE ring, camera, integrations.
- No launcher icon yet (system default is used).

## Current blockers

- None for Milestone 1 beyond human verification on hardware.

## Next test (required before Milestone 2)

On a Samsung Galaxy phone:

1. App launches and shows STATUS: STANDING BY.
2. Tap GRANT MICROPHONE → system dialog → allow → Microphone subsystem turns READY.
3. RECORD TEST → progress bar runs ~4 s → state RECORDED, clip shows duration and a peak
   level above ~5% when you spoke.
4. PLAY TEST → you hear your recording clearly through the phone speaker.
5. "Input device (actual)" shows the built-in mic; "Output device (actual)" shows the
   built-in speaker (or the connected headset if one is attached).
6. EMERGENCY MUTE during playback stops audio immediately.
