import com.egoriku.grodnoroads.extension.configureTargets

plugins {
    alias(libs.plugins.grodnoroads.multiplatform.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
}

kotlin {
    configureTargets(namespace = "com.egoriku.grodnoroads.foundation.uikit")

    sourceSets {
        commonMain.dependencies {
            implementation(projects.kmp.compose.foundation.icons)
            api(projects.kmp.compose.foundation.core)
            api(projects.kmp.compose.foundation.preview)

            api(libs.compose.foundation)
            api(libs.compose.material3)
            api(libs.compose.runtime)
            api(libs.compose.ui)
        }
    }
}
