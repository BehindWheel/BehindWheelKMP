import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.iosTarget

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrains.compose)
}

android {
    namespace = "com.egoriku.grodnoroads.appsettings"
}

kotlin {
    androidTarget()
    iosTarget()

    sourceSets {
        commonDependencies {
            implementation(projects.kmp.compose.commonUi)
            implementation(projects.kmp.compose.foundation.uikit)
            implementation(projects.kmp.shared.components)
            implementation(projects.kmp.shared.models)

            implementation(libs.decompose)
        }
        androidDependencies {
            implementation(projects.kmp.shared.resources)
            implementation(projects.libraries.localization)

            implementation(libs.androidx.browser)
            implementation(libs.androidx.core)
        }
    }
}