import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.applyTargets
import com.egoriku.grodnoroads.extension.commonDependencies

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
    alias(libs.plugins.kotlin.cocoapods)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.moko.resources)
}

android {
    namespace = "com.egoriku.grodnoroads.root"
}

kotlin {
    applyTargets()

    cocoapods {
        version = "1.0.0"

        homepage = "https://github.com/grodnoroads/GrodnoRoads"
        summary = "Shared functionality for iOS"

        pod("FirebaseCore", linkOnly = true)

        framework {
            baseName = "Root"

            export(libs.decompose)
            export(libs.essenty.lifecycle)
            export(libs.moko.resources)

            export(projects.kmp.features.appSettings)
            export(projects.kmp.features.guidance)
            export(projects.kmp.features.eventReporting)
            export(projects.kmp.features.specialEventReminder)
            export(projects.kmp.features.mainflow)
            export(projects.kmp.features.onboarding)
            export(projects.kmp.features.settings.alerts)
            export(projects.kmp.features.settings.appearance)
            export(projects.kmp.features.settings.changelog)
            export(projects.kmp.features.settings.faq)
            export(projects.kmp.features.settings.map)
            export(projects.kmp.features.tabs)
            export(projects.kmp.shared.analytics)
            export(projects.kmp.shared.models)
            export(projects.kmp.shared.resources)
            export(projects.kmp.libraries.coroutines)
            export(projects.kmp.libraries.location)
        }
    }

    sourceSets {
        commonDependencies {
            api(projects.kmp.features.appSettings)
            api(projects.kmp.features.guidance)
            api(projects.kmp.features.eventReporting)
            api(projects.kmp.features.specialEventReminder)
            api(projects.kmp.features.quickSettings)
            api(projects.kmp.features.mainflow)
            api(projects.kmp.features.onboarding)
            api(projects.kmp.features.settings.alerts)
            api(projects.kmp.features.settings.appearance)
            api(projects.kmp.features.settings.changelog)
            api(projects.kmp.features.settings.faq)
            api(projects.kmp.features.settings.map)
            api(projects.kmp.features.tabs)
            api(projects.kmp.shared.analytics)
            api(projects.kmp.shared.models)
            api(projects.kmp.libraries.coroutines)
            api(projects.kmp.libraries.location)
            implementation(projects.kmp.compose.foundation.uikit)
            implementation(projects.kmp.shared.crashlytics)
            implementation(projects.kmp.shared.geolocation)
            implementation(projects.kmp.shared.persistent)
            implementation(projects.kmp.shared.resources)
            implementation(projects.kmp.libraries.datastore)

            api(libs.decompose)
            api(libs.essenty.lifecycle)
            api(libs.moko.resources)

            implementation(libs.dev.gitlive.firebase.crashlytics)
            implementation(libs.dev.gitlive.firebase.database)
            implementation(libs.dev.gitlive.firebase.firestore)
            implementation(libs.koin.core)
            implementation(libs.mvikotlin)
            implementation(libs.mvikotlin.main)
        }
        androidDependencies {
            implementation(libs.decompose.compose)
            implementation(libs.koin.android)
        }
    }
}