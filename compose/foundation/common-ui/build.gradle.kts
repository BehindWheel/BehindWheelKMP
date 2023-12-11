plugins {
    id("grodnoroads.library")
    id("grodnoroads.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.foundation.common.ui"
}

dependencies {
    implementation(projects.compose.foundation.preview)
    implementation(projects.compose.foundation.theme)

    implementation(libs.androidx.compose.material3)
}