import com.egoriku.grodnoroads.extension.configureTargets

plugins {
    alias(libs.plugins.grodnoroads.multiplatform.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
}

kotlin {
    configureTargets(namespace = "com.egoriku.grodnoroads.foundation.preview")

    sourceSets {
        commonMain.dependencies {
            api(projects.kmp.compose.foundation.theme)

            api(libs.compose.ui.tooling.preview)
            implementation(libs.compose.material3)
        }
        androidMain.dependencies {
            api(libs.compose.ui.tooling)
        }
    }
}
