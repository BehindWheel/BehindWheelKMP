import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.iosTarget

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
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
            implementation(projects.kmp.compose.resources)
            implementation(projects.kmp.shared.components)
            implementation(projects.kmp.shared.models)

            implementation(libs.decompose)
        }
        androidDependencies {
            implementation(libs.androidx.browser)
            implementation(libs.androidx.core)
        }
    }
}