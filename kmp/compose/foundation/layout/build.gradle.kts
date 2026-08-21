import com.egoriku.grodnoroads.extension.configureTargets

plugins {
    alias(libs.plugins.grodnoroads.multiplatform.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
}

kotlin {
    configureTargets(namespace = "com.egoriku.grodnoroads.foundation.layout")

    sourceSets {
        commonMain.dependencies {
            api(libs.compose.foundation)
            api(libs.compose.runtime)
            api(libs.compose.ui)
        }
    }
}
