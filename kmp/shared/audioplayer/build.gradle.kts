import com.egoriku.grodnoroads.extension.configureTargets

plugins {
    alias(libs.plugins.grodnoroads.kmp.compose)
    alias(libs.plugins.grodnoroads.multiplatform.library)
}

kotlin {
    configureTargets(namespace = "com.egoriku.grodnoroads.shared.audioplayer")

    sourceSets {
        commonMain.dependencies {
            implementation(projects.kmp.libraries.logger)
            implementation(projects.kmp.compose.resources)

            implementation(libs.compose.foundation)
            implementation(libs.compose.runtime)
        }
        androidMain.dependencies {
            implementation(libs.androidx.core)
            implementation(libs.androidx.media)
        }
    }
}
