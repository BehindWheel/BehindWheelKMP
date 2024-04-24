import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.iosTarget

plugins {
    id("grodnoroads.kmplibrary")
    id("grodnoroads.compose")
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