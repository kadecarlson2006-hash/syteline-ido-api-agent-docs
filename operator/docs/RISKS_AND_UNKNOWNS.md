# Risks and Unknowns — OPERATOR

Status values: **UNKNOWN TO VERIFY**, **VERIFIED**, **PARTIALLY VERIFIED**, **BLOCKED**.
Update this file whenever a test answers a question.

| # | Item | Status | Notes / next test |
|---|------|--------|-------------------|
| 1 | Continuous Ray-Ban Meta Gen 2 microphone access for third-party apps | UNKNOWN TO VERIFY | Milestone 3/10. Meta Wearables Device Access Toolkit docs must be read first. Do not assume continuous streaming works. Fallback: phone mic → glasses speakers. |
| 2 | Meta AI coexistence (does Meta AI grab the mic/speaker or gestures?) | UNKNOWN TO VERIFY | Milestone 10 real-device test. |
| 3 | Bluetooth audio focus and route flapping between SCO/A2DP/LE Audio | UNKNOWN TO VERIFY | Milestone 2 diagnostics will log actual `routedDevice`; Milestone 10 tests with glasses. |
| 4 | Glasses battery impact of continuous audio | UNKNOWN TO VERIFY | Measure over 1 h sessions in Milestone 10. |
| 5 | Android background service restrictions (foreground service type `microphone`, Doze) | UNKNOWN TO VERIFY | Milestone 11. Continuous capture needs a foreground service with `FOREGROUND_SERVICE_MICROPHONE`; Android 14+ restricts starting mic FGS from background. |
| 6 | Ambient transcription cost | UNKNOWN TO VERIFY | Local VAD before cloud; UsageTracker in Milestone 8+. |
| 7 | OpenRouter latency (first token) | UNKNOWN TO VERIFY | Measure in Milestone 6 with the latency timeline. |
| 8 | ElevenLabs latency (first audio) and streaming support | UNKNOWN TO VERIFY | Milestone 9. Check current official docs for streaming/websocket endpoints before implementing. |
| 9 | Voice interruption behaviour (barge-in while Operator speaks) | UNKNOWN TO VERIFY | Milestone 9/11. |
| 10 | BLE ring compatibility (HID vs custom GATT) | UNKNOWN TO VERIFY | Milestone 15. Design around generic Android HID first. |
| 11 | Android microphone capture/playback on the phone itself | PARTIALLY VERIFIED | Milestone 1 code compiles (CI) but has not yet been run on a device — see CURRENT_STATUS.md "next test". |
| 12 | Build toolchain compatibility (AGP 9.3 + Kotlin 2.4.10 + Gradle 9.5.0) | PARTIALLY VERIFIED | `:core` builds and tests pass locally; `:app` is verified only via the GitHub Actions workflow. |
| 13 | `AudioRecord.routedDevice` / `AudioTrack.routedDevice` reliability on Samsung | UNKNOWN TO VERIFY | Some OEM builds return null until a few buffers are processed; the recorder polls after each read. |
| 14 | 16 kHz mono capture support on every input route | UNKNOWN TO VERIFY | Bluetooth SCO is 8/16 kHz; LE Audio may differ. Milestone 2 will display `AudioDeviceInfo.sampleRates`. |
| 15 | Meta developer documentation host (`wearables.developer.meta.com`) reachable from the dev environment | BLOCKED (in the CI/agent sandbox) | Read the docs from a normal workstation before Milestone 3. |
