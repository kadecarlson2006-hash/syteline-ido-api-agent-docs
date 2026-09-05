// :glasses-meta — the ONLY module that touches the Meta Wearables Device Access Toolkit.
// Included by settings.gradle.kts only when a GitHub Packages token is available (ADR-013).
plugins {
    id("com.android.library")
}

android {
    namespace = "com.operator.glasses.meta"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField("String", "MWDAT_VERSION", "\"${libs.versions.mwdat.get()}\"")
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    api(project(":core"))
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.mwdat.core)
    implementation(libs.mwdat.mockdevice)
}
