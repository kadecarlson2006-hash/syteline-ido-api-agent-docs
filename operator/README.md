# OPERATOR — Private Assistance System

A private AI companion that lives in smart glasses. An Android phone is the compute and
network hub; the glasses provide microphone and speaker. Operator listens when explicitly
activated, understands the conversation, and — most of the time — says nothing. Occasionally
it whispers something useful, corrective, or funny.

> Silence is the default. `NO_RESPONSE` is the most common outcome by design.

**Status:** Milestones 0 and 1 implemented; see [CURRENT_STATUS.md](CURRENT_STATUS.md).

## Hardware target

- Ray-Ban Meta Gen 2 smart glasses (Milestone 3+; isolated behind `MetaGlassesManager`)
- Samsung Galaxy flagship Android phone (Android 10 / API 29 minimum)

Until glasses access is proven, Operator works with the phone microphone and any Bluetooth
audio output.

## Architecture

Two Gradle modules today, more later:

| Module | Purpose |
|--------|---------|
| `:core` | Pure Kotlin/JVM. Domain model (`OperatorMode`, `WitLevel`, `OperatorState`), `OperatorStateManager`, provider contracts (`AIProvider`, `TTSProvider`, `TranscriptionProvider`, `MemoryRepository`), `ResponseDecision`, latency timeline, audio loopback state machine. No Android. |
| `:app` | Android app. Jetpack Compose UI, `AudioRecord`/`AudioTrack` implementations, permission handling, diagnostics. |

Full layout and data flow: [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md).
Decisions: [docs/DECISIONS.md](docs/DECISIONS.md). Unknowns: [docs/RISKS_AND_UNKNOWNS.md](docs/RISKS_AND_UNKNOWNS.md).

Planned backend (Milestone 4+): a modular monolith owning provider credentials, PostgreSQL +
pgvector memory, and streaming endpoints. The app never holds provider secrets.

## Setup

Requirements: JDK 17+, Android Studio (current stable) with SDK Platform 37, a device or
emulator running Android 10+.

```bash
cd operator
cp local.properties.example local.properties   # optional: tweak defaults, add sdk.dir
```

`local.properties` and `.env` are git-ignored. Only non-secret values go in
`local.properties`; secrets belong to the backend `.env` (template: `.env.example`).

## Build

```bash
cd operator

# Core module only — works on any machine with a JDK, no Android SDK needed
./gradlew :core:test -Poperator.skipAndroid=true

# Full build (requires Android SDK; set sdk.dir in local.properties or ANDROID_HOME)
./gradlew :app:assembleDebug

# All unit tests
./gradlew test
```

Install and launch on a connected device:

```bash
./gradlew :app:installDebug
adb shell am start -n com.operator.app/.MainActivity
adb logcat -s AndroidAudioRecorder AndroidAudioPlayer
```

### Building without the Android SDK

`:core` deliberately has no Android dependency. Passing `-Poperator.skipAndroid=true` excludes
`:app` from the build and keeps the Android Gradle Plugin off the classpath, so
`./gradlew :core:test -Poperator.skipAndroid=true` never resolves AGP or AndroidX and works on
locked-down CI agents and plain laptops. (All plugins otherwise share one root classpath — see
the comment in `build.gradle.kts`.)

## Testing

- `:core` unit tests: JUnit 5 + kotlinx-coroutines-test + Turbine. Run with
  `./gradlew :core:test`.
- `:app` unit tests: JUnit 4 (`./gradlew :app:testDebugUnitTest`).
- CI: `.github/workflows/operator-android.yml` runs core tests, app unit tests, and
  `assembleDebug` on every push touching `operator/`, and uploads the debug APK as an artifact.
- Device verification steps for the current milestone are in `CURRENT_STATUS.md`.

## Milestone 1 walk-through

1. Launch → header shows **STATUS: STANDING BY**.
2. **GRANT MICROPHONE** → allow.
3. **RECORD TEST** records ~4 s (progress bar). The clip stays in memory only.
4. **PLAY TEST** plays it back on the current default output (speaker, wired, or Bluetooth).
5. The Audio Test panel shows the device Android *actually* used for capture and playback
   (`routedDevice`), plus all available inputs/outputs.
6. **EMERGENCY MUTE** stops playback immediately and blocks further audio until released.

## Configuration

All keys are documented in `local.properties.example` (app) and `.env.example` (backend).
Highlights:

| Key | Purpose |
|-----|---------|
| `OPERATOR_DEFAULT_MODE`, `OPERATOR_DEFAULT_WIT` | Startup mode/wit |
| `OPERATOR_RECORD_TEST_DURATION_MILLIS` | Milestone 1 recording length |
| `OPERATOR_*_MODEL_ID` | Fast / deep / decision / vision model IDs (never hard-coded) |
| `OPERATOR_TTS_PROVIDER`, `OPERATOR_ELEVENLABS_VOICE_ID`, `OPERATOR_ELEVENLABS_MODEL_ID` | Voice |
| `ROLLING_CONTEXT_SECONDS`, `MIN_COMMENT_INTERVAL_SECONDS`, `MAX_COMMENTS_PER_5_MINUTES` | Anti-annoyance |

## Privacy model

- No raw ambient audio is ever written to disk. Milestone 1's clip is a `ShortArray` in RAM.
- Transcript logging is off by default (nothing is transcribed yet).
- Listening state is always visible on screen; EMERGENCY MUTE is one tap and absolute.
- Debug logs never contain API keys (the app has none).
- Ambient memory will require explicit request or an opt-in policy (Milestone 7+).

## Known limitations

- Milestone 1 is phone-only: no Bluetooth device selection, no glasses.
- No launcher icon.
- `:app` compilation is verified in CI; the authoring environment lacked the Android SDK.
- No release signing configuration.

## Roadmap

0. Project skeleton ✅  1. Phone audio loopback ✅ (pending device check)
2. Bluetooth audio diagnostics  3. Meta device access  4. Backend skeleton
5. Memory database v1  6. Basic text AI (OpenRouter)  7. Memory-aware text AI
8. Push to talk  9. ElevenLabs voice  10. Glasses audio  11. Rolling transcription
12. Response decision engine  13. Active Operator  14. Feedback learning
15. BLE ring / remote  16. Camera context  17. Work integrations
