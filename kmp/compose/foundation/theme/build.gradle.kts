import com.egoriku.grodnoroads.extension.configureTargets

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
}

kotlin {
    configureTargets(namespace = "com.egoriku.grodnoroads.foundation.theme")

    sourceSets {
        commonMain.dependencies {
            implementation(projects.kmp.compose.resources)

            implementation(libs.compose.material3)
        }
    }
}
