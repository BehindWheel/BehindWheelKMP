import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.setupIosTarget

plugins {
    id("grodnoroads.kmplibrary")
    id("grodnoroads.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.settings.alerts"
}

kotlin {
    androidTarget()
    setupIosTarget(baseName = "settings_alerts")

    sourceSets {
        commonDependencies {
            implementation(projects.kmp.shared.persistent)
            implementation(projects.kmp.libraries.coroutines)
            implementation(projects.kmp.libraries.datastore)

            implementation(libs.decompose)
            implementation(libs.dev.gitlive.firebase.firestore)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)

            implementation(libs.kotlin.collections)
            implementation(libs.kotlin.coroutines.core)
            implementation(libs.mvikotlin)
            implementation(libs.mvikotlin.extensions)
            implementation(libs.mvikotlin.main)
        }
        androidDependencies {
            implementation(projects.compose.foundation.core)
            implementation(projects.compose.foundation.preview)
            implementation(projects.compose.foundation.uikit)
            implementation(projects.compose.commonUi)

            implementation(projects.libraries.audioplayer)
            implementation(projects.libraries.resources)

            implementation(libs.androidx.compose.foundation)
            implementation(libs.androidx.compose.material3)
            implementation(libs.androidx.compose.material.icons)
        }
    }
}