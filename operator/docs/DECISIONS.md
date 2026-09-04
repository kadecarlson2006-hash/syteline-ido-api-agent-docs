# Architecture Decision Records — OPERATOR

Short records of decisions that would otherwise be re-litigated. Newest at the bottom.

---

## ADR-001: OpenRouter is the primary model gateway

**Status:** Accepted (design; implementation in Milestone 6)

Operator talks to models through an `AIProvider` interface. The first implementation is
OpenRouter, which gives one API and one bill across vendors, provider fallback, and easy model
swapping via configuration (`OPERATOR_FAST_MODEL_ID`, `OPERATOR_DEEP_MODEL_ID`, …). Direct
vendor providers may be added later where latency or features justify it. Model names are
never hard-coded.

## ADR-002: PostgreSQL + pgvector for memory

**Status:** Accepted (design; implementation in Milestone 5)

Memory is a core feature with relational structure (people, projects, commitments) *and*
semantic retrieval. One database that does both keeps operations simple. Normalised tables
first; embeddings in pgvector; no "everything in one JSON blob".

## ADR-003: Ambient raw audio is never stored permanently

**Status:** Accepted (in force from Milestone 1)

Audio lives in rolling in-memory buffers and is discarded automatically. The Milestone 1
loopback clip is a `ShortArray` in process memory and is dropped on DISCARD CLIP or process
death. Raw-audio logging is OFF by default and will remain a developer-only, explicit opt-in.

## ADR-004: Ray-Ban Meta integration is isolated behind `MetaGlassesManager`

**Status:** Accepted (design; implementation in Milestone 3)

Everything Meta-specific lives in one package behind one interface. The rest of Operator uses
standard Android audio routing, so the product still works with phone mic → glasses speakers
(or any Bluetooth headset) if third-party microphone access turns out to be unavailable.

## ADR-005: Provider secrets live in the backend, not the APK

**Status:** Accepted (in force from Milestone 0)

The Android app carries no OpenRouter/ElevenLabs/database credentials. `local.properties`
holds only non-secret defaults (mode, wit, model *IDs*, test durations) and feeds
`BuildConfig`. Secrets go in the backend `.env`. Both files are git-ignored with committed
`.example` templates.

## ADR-006: Toolchain — AGP 9.4.0, Gradle 9.6.0, Kotlin 2.4.10, Compose BOM 2026.08.00, compileSdk 37

**Status:** Accepted (Milestone 0, 2026-09)

- AGP 9.x provides built-in Kotlin: the `org.jetbrains.kotlin.android` plugin is *not*
  applied. Only `com.android.application` + `org.jetbrains.kotlin.plugin.compose`.
- Current AndroidX (core 1.19, lifecycle 2.11, Compose 1.12 via BOM 2026.08.00) refuses to
  compile against anything below compileSdk 37 (second CI run failed on exactly this), and
  AGP 9.4 is the release that supports API 37. AGP 9.4 requires Gradle ≥ 9.6.0.
- Kotlin 2.4.10's *tested* Gradle range ends at 9.5.0; it runs on 9.6 with at most a warning
  (Google's nowinandroid runs Kotlin 2.3 on Gradle 9.7). Re-check the Kotlin compatibility
  table on the next Kotlin bump.
- targetSdk stays at 36 for now: compileSdk only unlocks APIs, targetSdk opts into new
  runtime behaviour, which is a deliberate later step.
- Versions are centralised in `gradle/libs.versions.toml`. Plugins are placed on the root
  `buildscript` classpath (not per-module `plugins { alias(...) }`) so AGP, KGP, and the Compose
  compiler plugin share one class loader; the first CI run failed with
  `NoClassDefFoundError: com/android/build/gradle/api/BaseVariant` when they were split.
  `-Poperator.skipAndroid=true` omits AGP and `:app` for core-only builds.

## ADR-007: Two modules from day one: `:core` (pure JVM) and `:app` (Android)

**Status:** Accepted (Milestone 0)

Domain model, state manager, provider contracts, config parsing, latency timeline, decision
model, and the audio loopback state machine live in `:core` with no Android dependency. This
gives sub-second unit tests with fakes, keeps Android out of business logic, and lets the
core be compiled on machines without the Android SDK. `:app` implements the ports
(`AudioRecorder`, `AudioPlayer`) with `AudioRecord`/`AudioTrack` and hosts Compose UI.

## ADR-008: Manual dependency container, no DI framework (for now)

**Status:** Accepted (Milestone 0)

`OperatorContainer` wires the object graph by hand. Hilt/Koin would add build complexity
(annotation processing / KSP) for a graph of a dozen objects. Revisit when the graph or the
number of scopes grows; the container is the single place to swap.

## ADR-009: Silence is a first-class outcome

**Status:** Accepted (Milestone 0)

`ResponseCategory.NO_RESPONSE` exists from the first commit, `ResponseDecision.silence()` is
the canonical default, and `ResponseDecision.suppressed(reason)` lets local rules override a
model's `shouldSpeak=true`. `SilentDecisionEngine` is the only engine until Milestone 12.
The state manager also refuses COMMENT NOW while muted or OFF.

## ADR-010: The Operator project lives in `operator/` inside this repository

**Status:** Accepted (Milestone 0) — revisit

This repository previously contained only Syteline IDO documentation. Operator was placed in
an `operator/` subdirectory so nothing collides with the existing docs, README, or
`.gitignore`. The GitHub Actions workflow is scoped to `operator/**`. Moving Operator to its
own repository later is a `git subtree split` away.
