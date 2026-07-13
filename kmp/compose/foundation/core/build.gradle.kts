import com.egoriku.grodnoroads.extension.configureTargets

plugins {
    alias(libs.plugins.grodnoroads.multiplatform.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
}

kotlin {
    configureTargets(namespace = "com.egoriku.grodnoroads.foundation.core")

    sourceSets {
        commonMain.dependencies {
            api(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.material3.adaptive)
        }
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }
    }
}
