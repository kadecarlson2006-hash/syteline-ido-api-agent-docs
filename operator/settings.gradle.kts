// OPERATOR — Private Assistance System
// Multi-module Gradle build.
//
//   :core  pure Kotlin/JVM — domain model, state, provider contracts. No Android dependency.
//          Compiles and tests on any JDK 17+ machine without the Android SDK.
//   :app   Android application — Jetpack Compose UI, audio, permissions, diagnostics.
//
// Backend, memory, and provider modules will be added alongside these in later milestones.

pluginManagement {
    repositories {
        // Maven Central is first so that pure-JVM modules (:core) resolve without ever
        // touching Google's Maven repository. Android artifacts fall through to google().
        mavenCentral()
        gradlePluginPortal()
        google()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        google()
    }
}

rootProject.name = "operator"

include(":core")
include(":app")
