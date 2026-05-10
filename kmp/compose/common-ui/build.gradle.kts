import com.egoriku.grodnoroads.extension.configureTargets

plugins {
    alias(libs.plugins.grodnoroads.multiplatform.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
}

kotlin {
    configureTargets(namespace = "com.egoriku.grodnoroads.foundation.common.ui")

    sourceSets {
        commonMain.dependencies {
            implementation(projects.kmp.compose.foundation.icons)
            implementation(projects.kmp.compose.foundation.uikit)
            implementation(projects.kmp.compose.resources)

            implementation(libs.kotlin.collections)
        }
    }
}
