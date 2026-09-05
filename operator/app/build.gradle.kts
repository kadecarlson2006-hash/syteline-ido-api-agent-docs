import java.util.Properties

// :app — the Android application. AGP 9 provides built-in Kotlin, so no kotlin-android plugin.
plugins {
    id("com.android.application")             // versions: root build.gradle.kts classpath
    id("org.jetbrains.kotlin.plugin.compose")
}

// Developer configuration comes from local.properties (git-ignored) or the environment.
// Only NON-SECRET values are read here. Provider API keys never enter the APK (ADR-005).
val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) file.inputStream().use { load(it) }
}

fun configValue(key: String, default: String = ""): String =
    localProperties.getProperty(key)?.trim()?.takeIf { it.isNotEmpty() }
        ?: providers.environmentVariable(key).orNull?.trim()?.takeIf { it.isNotEmpty() }
        ?: default

fun stringField(key: String, default: String = "") = "\"${configValue(key, default).replace("\"", "\\\"")}\""

android {
    namespace = "com.operator.app"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.operator.app"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "0.2.0-m2"

        buildConfigField("String", "OPERATOR_DEFAULT_MODE", stringField("OPERATOR_DEFAULT_MODE", "STANDBY"))
        buildConfigField("String", "OPERATOR_DEFAULT_WIT", stringField("OPERATOR_DEFAULT_WIT", "NORMAL"))
        buildConfigField("String", "OPERATOR_BACKEND_URL", stringField("OPERATOR_BACKEND_URL"))
        buildConfigField("String", "OPERATOR_FAST_MODEL_ID", stringField("OPERATOR_FAST_MODEL_ID"))
        buildConfigField("String", "OPERATOR_DEEP_MODEL_ID", stringField("OPERATOR_DEEP_MODEL_ID"))
        buildConfigField("String", "OPERATOR_DECISION_MODEL_ID", stringField("OPERATOR_DECISION_MODEL_ID"))
        buildConfigField("String", "OPERATOR_VISION_MODEL_ID", stringField("OPERATOR_VISION_MODEL_ID"))
        buildConfigField("String", "OPERATOR_TTS_PROVIDER", stringField("OPERATOR_TTS_PROVIDER"))
        buildConfigField("String", "OPERATOR_ELEVENLABS_VOICE_ID", stringField("OPERATOR_ELEVENLABS_VOICE_ID"))
        buildConfigField("String", "OPERATOR_ELEVENLABS_MODEL_ID", stringField("OPERATOR_ELEVENLABS_MODEL_ID"))
        buildConfigField("String", "ROLLING_CONTEXT_SECONDS", stringField("ROLLING_CONTEXT_SECONDS"))
        buildConfigField("String", "MIN_COMMENT_INTERVAL_SECONDS", stringField("MIN_COMMENT_INTERVAL_SECONDS"))
        buildConfigField("String", "MAX_COMMENTS_PER_5_MINUTES", stringField("MAX_COMMENTS_PER_5_MINUTES"))
        buildConfigField("String", "OPERATOR_RECORD_TEST_DURATION_MILLIS", stringField("OPERATOR_RECORD_TEST_DURATION_MILLIS"))
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    implementation(project(":core"))

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)

    testImplementation(libs.junit4)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}
