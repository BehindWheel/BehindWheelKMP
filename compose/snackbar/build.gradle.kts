plugins {
    id("grodnoroads.library")
    id("grodnoroads.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.compose.snakbar"
}

dependencies {
    implementation(projects.compose.foundation.core)
    implementation(projects.compose.foundation.preview)
    implementation(projects.compose.foundation.theme)
    implementation(projects.compose.foundation.uikit)

    implementation(projects.libraries.extensions)

    implementation(libs.androidx.compose.material3)
}