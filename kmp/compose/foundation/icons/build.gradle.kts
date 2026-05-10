import com.egoriku.grodnoroads.extension.configureTargets

plugins {
    alias(libs.plugins.grodnoroads.multiplatform.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
}

kotlin {
    configureTargets(namespace = "com.egoriku.grodnoroads.foundation.icons")

    sourceSets {
        commonMain.dependencies {
            implementation(projects.kmp.compose.foundation.preview)

            implementation(libs.compose.material3)
            implementation(libs.compose.ui.tooling.preview)
        }
    }
}
