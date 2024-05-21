import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.iosTarget

plugins {
    alias(libs.plugins.grodnoroads.kmplibrary)
    alias(libs.plugins.grodnoroads.compose)
}

android {
    namespace = "com.egoriku.grodnoroads.onboarding"
}

kotlin {
    androidTarget()
    iosTarget()

    sourceSets {
        commonDependencies {
            compileOnly(libs.compose.stable.marker)
            implementation(libs.decompose)
        }
        androidDependencies {
            implementation(projects.compose.foundation.uikit)

            implementation(libs.decompose.compose)
        }
    }
}