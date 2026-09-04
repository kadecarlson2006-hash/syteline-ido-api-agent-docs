// Root build file.
//
// All Gradle plugins are put on ONE root classpath here so that the Android Gradle Plugin,
// the Kotlin Gradle Plugin, and the Compose compiler plugin share a single class loader.
// (Declaring AGP only in :app while Kotlin sits at the root splits them across parent/child
// loaders and AGP 9's built-in Kotlin support fails with NoClassDefFoundError.)
//
// Subprojects apply plugins by id WITHOUT a version (versions live in gradle/libs.versions.toml).
//
// -Poperator.skipAndroid=true leaves AGP off the classpath (and settings.gradle.kts drops :app),
// which lets :core build and test on machines that cannot reach Google's Maven repository.
buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
    }
    dependencies {
        classpath(libs.kotlin.gradlePlugin)
        classpath(libs.compose.gradlePlugin)
        val skipAndroid = providers.gradleProperty("operator.skipAndroid").map(String::toBoolean).getOrElse(false)
        if (!skipAndroid) {
            classpath(libs.android.gradlePlugin)
        }
    }
}
