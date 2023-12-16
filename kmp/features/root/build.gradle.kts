plugins {
    id("grodnoroads.kmplibrary")
    id("grodnoroads.compose")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.egoriku.grodnoroads.root"
}

kotlin {
    androidTarget()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "root"
            isStatic = true

            export(libs.decompose)
            export(libs.essenty.lifecycle)
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.kmp.features.onboarding)
                implementation(projects.kmp.features.mainflow)
                implementation(projects.kmp.libraries.datastore)

                implementation(libs.decompose)
                implementation(libs.dev.gitlive.firebase.firestore)

                implementation(project.dependencies.platform(libs.koin.bom))
                implementation(libs.koin.core)

                implementation(libs.mvikotlin)
                implementation(libs.mvikotlin.main)
            }
        }
        androidMain {
            dependencies {
                implementation(projects.compose.foundation.uikit)

                implementation(libs.androidx.compose.material3)
                implementation(libs.decompose.compose.jetpack)
                implementation(libs.koin.android)
            }
        }
    }
}