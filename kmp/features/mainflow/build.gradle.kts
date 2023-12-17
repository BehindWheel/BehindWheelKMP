import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.setupIosTarget

plugins {
    id("grodnoroads.kmplibrary")
    id("grodnoroads.compose")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.egoriku.grodnoroads.mainflow"
}

kotlin {
    androidTarget()
    setupIosTarget(baseName = "mainflow")

    sourceSets {
        commonDependencies {
            implementation(projects.kmp.features.tabs)
            implementation(projects.kmp.features.settings.changelog)
            implementation(projects.kmp.shared.models)

            implementation(libs.decompose)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
        }
        androidDependencies {
            implementation(projects.compose.foundation.uikit)

            implementation(libs.androidx.compose.material3)
            implementation(libs.decompose.compose.jetpack)
        }
    }
}