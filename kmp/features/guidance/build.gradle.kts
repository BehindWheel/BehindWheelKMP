import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.commonTestDependencies
import com.egoriku.grodnoroads.extension.iosTarget

plugins {
    alias(libs.plugins.grodnoroads.kmplibrary)
    alias(libs.plugins.grodnoroads.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.egoriku.grodnoroads.guidance"
}

kotlin {
    androidTarget()
    iosTarget()

    sourceSets {
        commonDependencies {
            implementation(projects.kmp.features.eventReporting)
            implementation(projects.kmp.features.quickSettings)
            implementation(projects.kmp.features.specialEventReminder)

            implementation(projects.kmp.shared.analytics)
            implementation(projects.kmp.shared.crashlytics)
            implementation(projects.kmp.shared.models)
            implementation(projects.kmp.shared.geolocation)
            implementation(projects.kmp.shared.persistent)

            implementation(projects.kmp.libraries.coroutines)
            implementation(projects.kmp.libraries.extensions)
            implementation(projects.kmp.libraries.logger)
            implementation(projects.kmp.libraries.uuid)

            compileOnly(libs.compose.stable.marker)

            implementation(libs.decompose)
            implementation(libs.dev.gitlive.firebase.database)
            implementation(libs.koin.core)
            implementation(libs.kotlin.collections)
            implementation(libs.kotlin.serialization.json)
            implementation(libs.mvikotlin)
            implementation(libs.mvikotlin.extensions)
            implementation(libs.mvikotlin.main)
        }
        androidDependencies {
            implementation(projects.compose.commonUi)
            implementation(projects.compose.foundation.uikit)
            implementation(projects.compose.maps.compose)
            implementation(projects.compose.locationRequester)
            implementation(projects.compose.snackbar)
            implementation(projects.kmp.shared.components)
            implementation(projects.kmp.shared.resources)
            implementation(projects.libraries.audioplayer)
            implementation(projects.libraries.localization)

            implementation(libs.androidx.activity.compose)

            implementation(libs.androidx.compose.material.icons)

            implementation(libs.balloon.compose)
            implementation(libs.decompose.compose)
            implementation(libs.google.app.update)
            implementation(libs.google.maps)
            implementation(libs.google.maps.utils)

            implementation(libs.koin.compose)
        }
        commonTestDependencies {
            implementation(libs.kotlin.datetime)
            implementation(libs.kotlin.test)
        }
    }
}