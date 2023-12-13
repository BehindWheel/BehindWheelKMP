plugins {
    id("grodnoroads.kmplibrary")
    id("grodnoroads.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.onboarding"
}

kotlin {
    androidTarget()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "onboarding"
            isStatic = true
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.decompose)
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