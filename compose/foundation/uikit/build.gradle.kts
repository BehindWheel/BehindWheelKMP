plugins {
    alias(libs.plugins.grodnoroads.library)
    alias(libs.plugins.grodnoroads.compose)
}

android {
    namespace = "com.egoriku.grodnoroads.foundation.uikit"
}

dependencies {
    api(projects.compose.foundation.core)
    api(projects.compose.foundation.preview)

    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material.icons)
}