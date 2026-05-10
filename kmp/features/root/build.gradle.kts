import com.egoriku.grodnoroads.extension.configureTargets

plugins {
    alias(libs.plugins.grodnoroads.multiplatform.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
    alias(libs.plugins.kotlin.cocoapods)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    configureTargets(namespace = "com.egoriku.grodnoroads.root")

    cocoapods {
        version = "1.0.0"
        ios.deploymentTarget = "15.0"

        podfile = project.file("../../../app/ios/Podfile")

        homepage = "https://github.com/grodnoroads/GrodnoRoads"
        summary = "Shared functionality for iOS"

        framework {
            baseName = "Root"
            isStatic = true

            export(libs.decompose)
            export(libs.essenty.backhandler)
            export(libs.essenty.lifecycle)

            export(projects.kmp.compose.mapsCompose)
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.kmp.compose.mapsCompose)

            implementation(projects.kmp.features.appSettings)
            implementation(projects.kmp.features.eventReporting)
            implementation(projects.kmp.features.guidance)
            implementation(projects.kmp.features.intro)
            implementation(projects.kmp.features.mainflow)
            implementation(projects.kmp.features.quickSettings)
            implementation(projects.kmp.features.settings.alerts)
            implementation(projects.kmp.features.settings.appearance)
            implementation(projects.kmp.features.settings.changelog)
            implementation(projects.kmp.features.settings.debugTools)
            implementation(projects.kmp.features.settings.faq)
            implementation(projects.kmp.features.settings.map)
            implementation(projects.kmp.features.specialEventReminder)
            implementation(projects.kmp.features.tabs)
            implementation(projects.kmp.compose.foundation.uikit)
            implementation(projects.kmp.compose.resources)
            implementation(projects.kmp.shared.analytics)
            implementation(projects.kmp.shared.crashlytics)
            implementation(projects.kmp.shared.geolocation)
            implementation(projects.kmp.shared.models)
            implementation(projects.kmp.shared.persistent)
            implementation(projects.kmp.libraries.datastore)
            implementation(projects.kmp.libraries.extensions)
            implementation(projects.kmp.libraries.location)
            implementation(projects.kmp.libraries.suntime)

            api(libs.decompose)
            api(libs.decompose.compose)
            api(libs.essenty.backhandler)
            api(libs.essenty.lifecycle)

            implementation(libs.dev.gitlive.firebase.analytics)
            implementation(libs.dev.gitlive.firebase.crashlytics)
            implementation(libs.dev.gitlive.firebase.database)
            implementation(libs.dev.gitlive.firebase.firestore)
            implementation(libs.essenty.lifecycle.coroutines)
            implementation(libs.koin.core)
            implementation(libs.kotlin.datetime)

            implementation(libs.bundles.mvikotlin)
        }
        androidMain.dependencies {
            implementation(libs.koin.android)

            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.analytics)
            implementation(libs.firebase.crashlytics)
            implementation(libs.firebase.database)
            implementation(libs.firebase.firestore)
        }
    }
}
