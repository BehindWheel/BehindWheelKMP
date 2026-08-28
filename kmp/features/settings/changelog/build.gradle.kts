import com.egoriku.grodnoroads.extension.configureTargets

plugins {
    alias(libs.plugins.grodnoroads.multiplatform.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    configureTargets(namespace = "com.egoriku.grodnoroads.features.settings.changelog")

    android {
        withHostTest {}
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.kmp.compose.commonUi)
            implementation(projects.kmp.compose.foundation.icons)
            implementation(projects.kmp.compose.foundation.navigation)
            implementation(projects.kmp.compose.foundation.uikit)
            implementation(projects.kmp.compose.resources)
            implementation(projects.kmp.compose.snackbar)
            implementation(projects.kmp.shared.auth)
            implementation(projects.kmp.shared.components)
            implementation(projects.kmp.shared.crashlytics)
            implementation(projects.kmp.shared.formatter)
            implementation(projects.kmp.libraries.extensions)

            implementation(libs.decompose)
            implementation(libs.decompose.compose)
            implementation(libs.dev.gitlive.firebase.firestore)
            implementation(libs.essenty.lifecycle.coroutines)
            implementation(libs.koin.core)

            implementation(libs.bundles.mvikotlin)
        }
        androidMain.dependencies {
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.firestore)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
