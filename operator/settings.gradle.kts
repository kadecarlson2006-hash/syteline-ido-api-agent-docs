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
//
// Meta Wearables Device Access Toolkit (:glasses-meta) is published on GitHub Packages, which
// requires a token with read:packages even for public artifacts. The module is included only
// when a token is available (env GITHUB_TOKEN or `github_token` in local.properties), or when
// forced with -Poperator.metaSdk=true|false. Without it the app builds with NoGlassesProvider.

import java.util.Properties

val localProperties = Properties().apply {
    val file = File(rootDir, "local.properties")
    if (file.exists()) file.inputStream().use { load(it) }
}
val githubPackagesToken: String? =
    System.getenv("GITHUB_TOKEN")?.takeIf { it.isNotBlank() } ?: localProperties.getProperty("github_token")?.takeIf { it.isNotBlank() }
val metaSdkEnabled: Boolean =
    providers.gradleProperty("operator.metaSdk").map(String::toBoolean).getOrElse(githubPackagesToken != null)
gradle.extra["operatorMetaSdkEnabled"] = metaSdkEnabled

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
        if (metaSdkEnabled) {
            maven {
                name = "MetaWearablesDAT"
                url = uri("https://maven.pkg.github.com/facebook/meta-wearables-dat-android")
                credentials {
                    username = "" // not needed by GitHub Packages
                    password = githubPackagesToken ?: ""
                }
                content { includeGroup("com.meta.wearable") }
            }
        }
    }
}

rootProject.name = "operator"

val skipAndroid = providers.gradleProperty("operator.skipAndroid").map(String::toBoolean).getOrElse(false)

include(":core")
if (!skipAndroid) {
    include(":app")
    if (metaSdkEnabled) {
        include(":glasses-meta")
    }
}
