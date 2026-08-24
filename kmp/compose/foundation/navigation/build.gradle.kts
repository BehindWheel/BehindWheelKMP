import com.egoriku.grodnoroads.extension.configureTargets

plugins {
    alias(libs.plugins.grodnoroads.multiplatform.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
}

kotlin {
    configureTargets(namespace = "com.egoriku.grodnoroads.foundation.navigation")

    sourceSets {
        commonMain.dependencies {
            api(libs.decompose)
            api(libs.decompose.compose)

            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
        }
    }
}
