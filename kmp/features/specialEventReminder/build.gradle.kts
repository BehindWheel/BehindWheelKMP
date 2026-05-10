import com.egoriku.grodnoroads.extension.configureTargets

plugins {
    alias(libs.plugins.grodnoroads.multiplatform.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    configureTargets(namespace = "com.egoriku.grodnoroads.specialevent")

    android {
        withHostTest {}
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.kmp.compose.commonUi)
            implementation(projects.kmp.compose.foundation.uikit)
            implementation(projects.kmp.compose.resources)
            implementation(projects.kmp.libraries.extensions)
            implementation(projects.kmp.libraries.logger)

            implementation(libs.decompose)
            implementation(libs.essenty.lifecycle.coroutines)
            implementation(libs.koin.core)
            implementation(libs.kotlin.coroutines)
            implementation(libs.kotlin.datetime)

            implementation(libs.bundles.mvikotlin)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
