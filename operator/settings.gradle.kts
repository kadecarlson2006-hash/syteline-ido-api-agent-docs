// OPERATOR — Private Assistance System
// Multi-module Gradle build.
//
//   :core  pure Kotlin/JVM — domain model, state, provider contracts. No Android dependency.
//          Compiles and tests on any JDK 17+ machine without the Android SDK.
//   :app   Android application — Jetpack Compose UI, audio, permissions, diagnostics.
//
// Backend, memory, and provider modules will be added alongside these in later milestones.
//
// Core-only builds (no Android SDK / no access to Google's Maven repository):
//     ./gradlew :core:test -Poperator.skipAndroid=true
// The property excludes :app and keeps the Android Gradle Plugin off the build classpath.

pluginManagement {
    repositories {
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

val skipAndroid = providers.gradleProperty("operator.skipAndroid").map(String::toBoolean).getOrElse(false)

include(":core")
if (!skipAndroid) {
    include(":app")
}
