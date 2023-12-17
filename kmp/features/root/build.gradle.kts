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
    }

    sourceSets {
        commonDependencies {
            implementation(projects.kmp.features.onboarding)
            implementation(projects.kmp.features.mainflow)
            implementation(projects.kmp.libraries.datastore)

            api(libs.decompose)
            api(libs.dev.gitlive.firebase.firestore)

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