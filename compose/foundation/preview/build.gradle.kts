plugins {
    id("grodnoroads.library")
    id("grodnoroads.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.foundation.preview"
}

dependencies {
    implementation(projects.compose.foundation.theme)

    implementation(libs.androidx.compose.material3)

    api(libs.androidx.compose.ui.tooling.preview)
    debugApi(libs.androidx.compose.ui.tooling)
}