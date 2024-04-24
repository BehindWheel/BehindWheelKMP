import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.iosTarget

plugins {
    alias(libs.plugins.grodnoroads.kmplibrary)
    alias(libs.plugins.grodnoroads.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.egoriku.grodnoroads.tabs"
}

kotlin {
    androidTarget()
    iosTarget()

    sourceSets {
        commonDependencies {
            implementation(projects.kmp.features.appSettings)
            implementation(projects.kmp.features.guidance)
            implementation(projects.kmp.features.eventReporting)

            implementation(projects.kmp.shared.models)

            implementation(projects.kmp.libraries.coroutines)
            implementation(projects.kmp.libraries.resources)

            compileOnly(libs.compose.stable.marker)
            implementation(libs.decompose)
        }
        androidDependencies {
            implementation(projects.compose.foundation.uikit)
            implementation(projects.libraries.resources)

            implementation(libs.androidx.compose.material3.windowsize)
            implementation(libs.decompose.compose.jetpack)
            implementation(libs.kotlin.collections)
        }
    }
}