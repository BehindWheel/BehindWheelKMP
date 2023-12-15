plugins {
    id("grodnoroads.kmplibrary")
    id("grodnoroads.compose")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.egoriku.grodnoroads.mainflow"
}

kotlin {
    androidTarget()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "mainflow"
            isStatic = true
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.kmp.features.tabs)
                implementation(projects.kmp.features.settings.changelog)
                implementation(projects.kmp.shared.models)

                implementation(libs.decompose)

                implementation(project.dependencies.platform(libs.koin.bom))
                implementation(libs.koin.core)
            }
        }
        androidMain {
            dependencies {
                implementation(projects.compose.foundation.uikit)

                implementation(libs.androidx.compose.material3)
                implementation(libs.decompose.compose.jetpack)
            }
        }
    }
}