# OPERATOR — Architecture

## Modules

```
operator/
├── core/   pure Kotlin/JVM — no Android
│   └── com.operator.core
│       ├── model         OperatorMode, WitLevel, OperatorStatus, OperatorState, Subsystem
│       ├── state         OperatorStateManager (StateFlow + events)
│       ├── config        OperatorConfig (+ canonical key names)
│       ├── audio         AudioRoute, RouteSelection, PcmClip, RecordingState,
│       │                 AudioRecorder/AudioPlayer ports, AudioLoopbackController
│       ├── diagnostics   LatencyTimeline, DiagnosticsSnapshot, RouteEventLog
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
        ├── permissions   MicrophonePermission, BluetoothPermission
        ├── audio         AudioRouteMapper, AudioRouteMonitor, CommunicationLink,
        │                 AndroidAudioRecorder, AndroidAudioPlayer, AudioSubsystemReporter
        ├── bluetooth     BluetoothStatusMonitor (adapter state, paired devices)
        └── ui            OperatorViewModel, OperatorUiState, OperatorScreen, theme, components
```

Future packages, each behind a `core` interface: `meta` (MetaGlassesManager), `remote`
(OperatorRemoteController), `camera` (CameraContextManager), `usage` (UsageTracker),
`backend` (HTTP/WebSocket client), plus a separate `backend/` service directory.

## Route selection (Milestone 2)

```
INPUT / OUTPUT chips ──▶ AudioLoopbackController.selectInput/selectOutput ──▶ RouteSelection
                                                                                    │
  no selection ─────────────────▶ platform default                                  │
  wired / USB / built-in ───────▶ setPreferredDevice(AudioDeviceInfo)               ▼
  Bluetooth SCO / BLE headset ──▶ CommunicationLink.use(sink) {                AndroidAudioRecorder
                                    MODE_IN_COMMUNICATION                      AndroidAudioPlayer
                                    setCommunicationDevice(sink) + wait ≤4 s        │
                                    capture VOICE_COMMUNICATION /                   ▼
                                    play USAGE_VOICE_COMMUNICATION            routedDevice ──▶ "actual"
                                  } finally clearCommunicationDevice()        RouteEventLog ──▶ panel + logcat
AudioRouteMonitor: device add/remove + OnCommunicationDeviceChangedListener ──▶ AudioRoutes, prunes vanished selections
```

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
