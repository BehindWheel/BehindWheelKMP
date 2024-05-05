import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.iosTarget

plugins {
    alias(libs.plugins.grodnoroads.compose)
    alias(libs.plugins.grodnoroads.kmplibrary)
}

android {
    namespace = "com.egoriku.grodnoroads.appsettings"
}

kotlin {
    androidTarget()
    iosTarget()

    sourceSets {
        commonDependencies {
            implementation(projects.kmp.shared.components)
            implementation(projects.kmp.shared.models)

            compileOnly(libs.compose.stable.marker)
            implementation(libs.decompose)
        }
        androidDependencies {
            implementation(projects.compose.foundation.uikit)
            implementation(projects.compose.commonUi)
            implementation(projects.kmp.shared.resources)
            implementation(projects.libraries.localization)

            implementation(libs.androidx.browser)
            implementation(libs.androidx.core)
        }
    }
}