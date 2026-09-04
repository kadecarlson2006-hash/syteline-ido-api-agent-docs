// Root build file.
//
// Only JVM-side plugins are declared here (apply false). The Android Gradle Plugin is
// declared solely in :app so that `:core` can be configured and tested on machines that
// have no Android SDK (see README "Building without the Android SDK").
plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.compose) apply false
}
