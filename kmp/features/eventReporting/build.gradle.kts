import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.iosTarget

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
}

android {
    namespace = "com.egoriku.grodnoroads.eventreporting"
}

kotlin {
    androidTarget()
    iosTarget()

    sourceSets {
        commonDependencies {
            implementation(projects.kmp.compose.foundation.uikit)
            implementation(projects.kmp.compose.resources)
            implementation(projects.kmp.shared.analytics)
            implementation(projects.kmp.shared.models)
            implementation(projects.kmp.libraries.coroutines)
            implementation(projects.kmp.libraries.extensions)
            implementation(projects.kmp.libraries.location)

            implementation(libs.decompose)
            implementation(libs.dev.gitlive.firebase.database)
            implementation(libs.koin.core)
            implementation(libs.kotlin.collections)
            implementation(libs.kotlin.coroutines)
            implementation(libs.mvikotlin)
            implementation(libs.mvikotlin.extensions)
            implementation(libs.mvikotlin.main)
        }
        androidDependencies {
            implementation(libs.androidx.activity.compose)
        }
    }
}