import com.egoriku.grodnoroads.extension.applyTargets
import com.egoriku.grodnoroads.extension.commonDependencies

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
}

android {
    namespace = "com.egoriku.grodnoroads.foundation.uikit"
}

kotlin {
    applyTargets()

    sourceSets {
        commonDependencies {
            implementation(projects.kmp.compose.foundation.icons)
            api(projects.kmp.compose.foundation.core)
            api(projects.kmp.compose.foundation.preview)

            api(compose.foundation)
            api(compose.material3)
            api(compose.runtime)
            api(compose.ui)
        }
    }
}