# Risks and Unknowns — OPERATOR

Status values: **UNKNOWN TO VERIFY**, **VERIFIED**, **PARTIALLY VERIFIED**, **BLOCKED**.
Update this file whenever a test answers a question.

| # | Item | Status | Notes / next test |
|---|------|--------|-------------------|
| 1 | Continuous Ray-Ban Meta Gen 2 microphone access for third-party apps | PARTIALLY VERIFIED | **Via the Meta toolkit: UNSUPPORTED** (0.9.0 has no audio API; docs/META_GLASSES.md). Via Bluetooth HFP/SCO as a plain headset: UNKNOWN until Milestone 10. Fallback phone mic → glasses speakers stands. |
| 2 | Meta AI coexistence (does Meta AI grab the mic/speaker or gestures?) | UNKNOWN TO VERIFY | SDK docs: a session is paused when another experience takes over the device. Captouch gestures act on the SDK stream, not on apps. Milestone 10 real-device test. |
| 3 | Bluetooth audio focus and route flapping between SCO/A2DP/LE Audio | UNKNOWN TO VERIFY | Milestone 2 diagnostics log every actual `routedDevice` and communication-device change; run the CURRENT_STATUS.md Milestone 2 checks with a headset, then Milestone 10 with glasses. |
| 4 | Glasses battery impact of continuous audio | UNKNOWN TO VERIFY | Measure over 1 h sessions in Milestone 10. |
| 5 | Android background service restrictions (foreground service type `microphone`, Doze) | UNKNOWN TO VERIFY | Milestone 11. Continuous capture needs a foreground service with `FOREGROUND_SERVICE_MICROPHONE`; Android 14+ restricts starting mic FGS from background. |
| 6 | Ambient transcription cost | UNKNOWN TO VERIFY | Local VAD before cloud; UsageTracker in Milestone 8+. |
| 7 | OpenRouter latency (first token) | UNKNOWN TO VERIFY | Measure in Milestone 6 with the latency timeline. |
| 8 | ElevenLabs latency (first audio) and streaming support | UNKNOWN TO VERIFY | Milestone 9. Check current official docs for streaming/websocket endpoints before implementing. |
| 9 | Voice interruption behaviour (barge-in while Operator speaks) | UNKNOWN TO VERIFY | Milestone 9/11. |
| 10 | BLE ring compatibility (HID vs custom GATT) | UNKNOWN TO VERIFY | Milestone 15. Design around generic Android HID first. |
| 11 | Android microphone capture/playback on the phone itself | PARTIALLY VERIFIED | Milestone 1 code compiles and unit-tests in CI but has not yet been run on a device — see CURRENT_STATUS.md "next test". |
| 12 | Build toolchain compatibility (AGP 9.4 + Kotlin 2.4.10 + Gradle 9.6.0) | VERIFIED | `:core` builds/tests locally and in CI; `:app` assembles and unit-tests in CI (run #3 green). |
| 13 | `AudioRecord.routedDevice` / `AudioTrack.routedDevice` reliability on Samsung | UNKNOWN TO VERIFY | Recorder/player poll after every buffer and log "routedDevice was never reported" if it stays null; look for that line in the route log. |
| 14 | 16 kHz mono capture support on every input route | UNKNOWN TO VERIFY | Bluetooth SCO is 8/16 kHz; LE Audio may differ. The Bluetooth diagnostics panel now shows `sampleRates`/`channelCounts`/`encodings` per device (empty list = arbitrary rates). |
| 15 | Meta developer documentation host (`wearables.developer.meta.com`) reachable from the dev environment | PARTIALLY VERIFIED | Host still blocked in the sandbox; the official SDK repository (README, CHANGELOG, AGENTS.md, skills, samples) was used instead. Cross-check the hosted docs from a workstation. |
| 16 | `setCommunicationDevice` bring-up time and whether Samsung confirms the device via `getCommunicationDevice()` | UNKNOWN TO VERIFY | `CommunicationLink` waits up to 4 s and logs "NOT confirmed" otherwise; capture proceeds either way so the route log shows what really happened. |
| 17 | Bluetooth device names in `AudioDeviceInfo.productName` / `getAddress()` without BLUETOOTH_CONNECT | UNKNOWN TO VERIFY | The reference lists no permission for either; if names come back blank, compare against the paired list (which does need BLUETOOTH_CONNECT). |
| 18 | Ray-Ban Meta appear as classic HFP/A2DP or LE Audio (`TYPE_BLE_HEADSET`) | UNKNOWN TO VERIFY | Determines which link path Milestone 10 uses; read it off the device table. |
| 19 | GitHub Actions `GITHUB_TOKEN` can download public GitHub Packages from another repository | UNKNOWN TO VERIFY | First Milestone 3 CI run decides; fallback is a repo secret `MWDAT_GITHUB_TOKEN` (PAT with read:packages). |
| 20 | Meta SDK 0.9.0 AAR compatibility with AGP 9.4 / Kotlin 2.4 / compileSdk 37 | UNKNOWN TO VERIFY | Samples use AGP 8.11 / Kotlin 2.2; CI compile of `:glasses-meta` decides. |
| 21 | Registration return deep link: does the URL scheme need to be declared anywhere besides the manifest? | UNKNOWN TO VERIFY | Samples only declare the intent filter; verify the round trip on the phone. |
| 22 | Device type string reported for Ray-Ban Meta Gen 2 (classic vs `META_GLASSES`) and Bluetooth profile (HFP vs LE Audio) | UNKNOWN TO VERIFY | Read off the Glasses panel and the Bluetooth device table. |
