plugins {
    id("grodnoroads.kmplibrary")
    id("grodnoroads.compose")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.egoriku.grodnoroads.tabs"
}

kotlin {
    androidTarget()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "tabs"
            isStatic = true
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.kmp.features.appSettings)
                implementation(projects.kmp.shared.models)

                implementation(libs.decompose)
            }
        }
        androidMain {
            dependencies {
                implementation(projects.compose.foundation.core)
                implementation(projects.compose.foundation.uikit)
                implementation(projects.libraries.resources)

                implementation(libs.androidx.compose.material3)
                implementation(libs.androidx.compose.material3.window)
                implementation(libs.decompose.compose.jetpack)
                implementation(libs.kotlin.collections)
            }
        }
    }
}