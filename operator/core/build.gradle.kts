// :core — pure Kotlin/JVM. No Android dependencies allowed in this module.
//
// Holds the domain model (OperatorMode, WitLevel, OperatorState), the state manager,
// provider contracts (AIProvider, TTSProvider, TranscriptionProvider, MemoryRepository),
// diagnostics/latency types, and the ResponseDecision model. Everything here is testable
// with plain JUnit on any machine with a JDK, which keeps the fast feedback loop fast.
plugins {
    id("org.jetbrains.kotlin.jvm") // version: root build.gradle.kts classpath
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        allWarningsAsErrors.set(true)
    }
}

dependencies {
    api(libs.kotlinx.coroutines.core)

    testImplementation(libs.kotlin.test.junit5)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
    testRuntimeOnly(libs.junit.platform.launcher)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    testLogging {
        events("passed", "failed", "skipped")
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}
