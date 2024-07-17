import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.applyTargets
import com.egoriku.grodnoroads.extension.commonDependencies

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
}

android {
    namespace = "com.egoriku.grodnoroads.foundation.icons"

    buildFeatures {
        compose = true
    }
}

kotlin {
    applyTargets()

    sourceSets {
        commonDependencies {
            implementation(compose.material3)
        }
        androidDependencies {
            implementation(projects.kmp.compose.foundation.preview)

            implementation(compose.preview)
            implementation(compose.uiTooling)
        }
    }
}