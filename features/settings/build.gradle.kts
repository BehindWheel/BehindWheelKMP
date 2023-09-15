plugins {
    id("grodnoroads.library")
    id("grodnoroads.library.compose")
    id("kotlin-parcelize")
}

android {
    namespace = "com.egoriku.grodnoroads.settings"
}

dependencies {
    implementation(projects.libraries.foundation)
    implementation(projects.libraries.resources)

    implementation(projects.shared.appComponent)

    implementation(libs.androidx.browser)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(libs.decompose)
    implementation(libs.decompose.compose.jetpack)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)
}