import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.setupIosTarget

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
    setupIosTarget(baseName = "root") {
        export(libs.decompose)
        export(libs.essenty.lifecycle)

        export(projects.kmp.features.appSettings)
        export(projects.kmp.features.mainflow)
        export(projects.kmp.features.onboarding)
        export(projects.kmp.features.settings.alerts)
        export(projects.kmp.features.settings.changelog)
        export(projects.kmp.features.settings.faq)
        export(projects.kmp.features.tabs)
        export(projects.kmp.libraries.extensions)
    }

    sourceSets {
        commonDependencies {
            api(projects.kmp.features.appSettings)
            api(projects.kmp.features.mainflow)
            api(projects.kmp.features.onboarding)
            api(projects.kmp.features.settings.alerts)
            api(projects.kmp.features.settings.changelog)
            api(projects.kmp.features.settings.faq)
            api(projects.kmp.features.tabs)
            implementation(projects.kmp.libraries.crashlytics)
            implementation(projects.kmp.libraries.datastore)
            api(projects.kmp.libraries.extensions)

            api(libs.decompose)
            implementation(libs.dev.gitlive.firebase.crashlytics)
            implementation(libs.dev.gitlive.firebase.firestore)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)

            implementation(libs.mvikotlin)
            implementation(libs.mvikotlin.main)
        }
        androidDependencies {
            implementation(projects.compose.foundation.uikit)

            implementation(libs.androidx.compose.material3)
            implementation(libs.decompose.compose.jetpack)
            implementation(libs.koin.android)
        }
    }
}