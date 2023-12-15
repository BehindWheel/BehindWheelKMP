plugins {
    id("grodnoroads.kmplibrary")
    id("grodnoroads.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.appsettings"
}

kotlin {
    androidTarget()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "appsettings"
            isStatic = true
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.kmp.shared.components)
                implementation(projects.kmp.shared.models)

                implementation(project.dependencies.platform(libs.koin.bom))
                implementation(libs.koin.core)
                implementation(libs.decompose)
            }
        }
        androidMain {
            dependencies {
                implementation(projects.compose.foundation.preview)
                implementation(projects.compose.foundation.theme)
                implementation(projects.compose.foundation.uikit)
                implementation(projects.compose.commonUi)

                implementation(projects.libraries.resources)

                implementation(libs.androidx.browser)
                implementation(libs.androidx.core)
                implementation(libs.androidx.compose.foundation)
                implementation(libs.androidx.compose.material3)
                implementation(libs.androidx.compose.material.icons)
            }
        }
    }
}