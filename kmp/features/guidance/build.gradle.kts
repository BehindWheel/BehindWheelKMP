import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.applyTargets
import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.commonTestDependencies

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.egoriku.grodnoroads.guidance"
}

kotlin {
    applyTargets()

    sourceSets {
        commonDependencies {
            implementation(projects.kmp.features.eventReporting)
            implementation(projects.kmp.features.quickSettings)
            implementation(projects.kmp.features.specialEventReminder)

            implementation(projects.kmp.compose.commonUi)
            implementation(projects.kmp.compose.foundation.icons)
            implementation(projects.kmp.compose.foundation.uikit)
            implementation(projects.kmp.compose.mapsCompose)
            implementation(projects.kmp.compose.locationRequester)
            implementation(projects.kmp.compose.resources)
            implementation(projects.kmp.compose.snackbar)

            implementation(projects.kmp.shared.analytics)
            implementation(projects.kmp.shared.audioplayer)
            implementation(projects.kmp.shared.components)
            implementation(projects.kmp.shared.crashlytics)
            implementation(projects.kmp.shared.formatter)
            implementation(projects.kmp.shared.models)
            implementation(projects.kmp.shared.geolocation)
            implementation(projects.kmp.shared.persistent)
            implementation(projects.kmp.shared.resources)

            implementation(projects.kmp.libraries.coroutines)
            implementation(projects.kmp.libraries.extensions)
            implementation(projects.kmp.libraries.location)
            implementation(projects.kmp.libraries.logger)

            implementation(libs.decompose)
            implementation(libs.dev.gitlive.firebase.database)
            implementation(libs.koin.compose)
            implementation(libs.kotlin.collections)
            implementation(libs.kotlin.serialization.json)
            implementation(libs.mvikotlin)
            implementation(libs.mvikotlin.extensions)
            implementation(libs.mvikotlin.main)
        }
        androidDependencies {
            implementation(libs.androidx.activity.compose)

            implementation(libs.balloon.compose)
            implementation(libs.decompose.compose)
            implementation(libs.google.app.update)
            implementation(libs.google.maps)
            implementation(libs.google.maps.utils)
        }
        commonTestDependencies {
            implementation(libs.kotlin.datetime)
            implementation(libs.kotlin.test)
        }
    }
}