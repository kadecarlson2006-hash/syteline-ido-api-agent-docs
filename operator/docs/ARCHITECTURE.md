# OPERATOR — Architecture

## Modules

```
operator/
├── core/   pure Kotlin/JVM — no Android
│   └── com.operator.core
│       ├── model         OperatorMode, WitLevel, OperatorStatus, OperatorState, Subsystem
│       ├── state         OperatorStateManager (StateFlow + events)
│       ├── config        OperatorConfig (+ canonical key names)
│       ├── audio         AudioRoute, PcmClip, RecordingState, AudioRecorder/AudioPlayer ports,
│       │                 AudioLoopbackController (Milestone 1 state machine)
│       ├── diagnostics   LatencyTimeline, DiagnosticsSnapshot
│       ├── decision      ResponseCategory, ResponseDecision, ResponseDecisionEngine, SilentDecisionEngine
│       ├── ai            AIProvider contract            (Milestone 6)
│       ├── tts           TTSProvider contract           (Milestone 8/9)
│       ├── transcription TranscriptionProvider contract (Milestone 8)
│       └── memory        MemoryRepository contract      (Milestone 5)
└── app/    Android — Jetpack Compose
    └── com.operator.app
        ├── OperatorApplication, MainActivity
        ├── di            OperatorContainer (manual wiring)
        ├── config        BuildConfigLoader
        ├── permissions   MicrophonePermission
        ├── audio         AudioRouteMapper, AudioRouteMonitor, AndroidAudioRecorder,
        │                 AndroidAudioPlayer, AudioSubsystemReporter
        └── ui            OperatorViewModel, OperatorUiState, OperatorScreen, theme, components
```

Future packages, each behind a `core` interface: `meta` (MetaGlassesManager), `remote`
(OperatorRemoteController), `camera` (CameraContextManager), `usage` (UsageTracker),
`backend` (HTTP/WebSocket client), plus a separate `backend/` service directory.

## Data flow (Milestone 1)

```
MainActivity ──permission result──▶ MicrophonePermission ──┐
                                                           ▼
OperatorViewModel ──recordTest()──▶ AudioLoopbackController ──▶ AndroidAudioRecorder (AudioRecord)
                                   │  state: IDLE→RECORDING→RECORDED            │ routedDevice
                                   ├──playTest()────────────▶ AndroidAudioPlayer (AudioTrack)
                                   │  state: RECORDED→PLAYING→RECORDED          │ routedDevice
                                   ▼
                        AudioSubsystemReporter ──▶ OperatorStateManager.updateSubsystem(MIC / OUTPUT)
                                                                   │
                        AudioRouteMonitor (AudioDeviceCallback) ───┘
                                                                   ▼
                        combine(...) ──▶ OperatorUiState ──▶ OperatorScreen (Compose)
```

## Principles encoded in code

- **Silence is default**: `ResponseDecision.silence()`, `SilentDecisionEngine`,
  `OperatorStateManager.commentNow()` refuses while muted/OFF.
- **Mute is absolute**: `OperatorState.status` reports MUTED regardless of mode; the
  ViewModel cancels any playback on `EmergencyMuteEngaged`.
- **Nothing hard-coded**: model IDs, voice IDs, providers are nullable config values.
- **Privacy**: PCM lives in memory only; no files, no uploads.
- **Testability**: every Android side effect sits behind a `core` port with a fake in tests.
