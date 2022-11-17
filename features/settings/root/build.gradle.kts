plugins {
    id("grodnoroads.library")
    id("grodnoroads.library.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.settings.root"
}

dependencies {
    implementation(projects.libraries.foundation)
    implementation(projects.libraries.resources)

    implementation(libs.androidx.browser)

    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(libs.koin.android)
}