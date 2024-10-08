import com.egoriku.grodnoroads.extension.applyTargets
import com.egoriku.grodnoroads.extension.commonDependencies

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
}

android {
    namespace = "com.egoriku.grodnoroads.foundation.common.ui"
}
kotlin {
    applyTargets()

    sourceSets {
        commonDependencies {
            implementation(projects.kmp.compose.foundation.icons)
            implementation(projects.kmp.compose.foundation.uikit)
            implementation(projects.kmp.compose.resources)

            implementation(libs.kotlin.collections)
        }
    }
}
