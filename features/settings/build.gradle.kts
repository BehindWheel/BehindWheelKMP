plugins {
    id("grodnoroads.library")
    id("grodnoroads.compose")
    id("kotlin-parcelize")
}

android {
    namespace = "com.egoriku.grodnoroads.settings"
}

dependencies {
    implementation(projects.compose.foundation.commonUi)
    implementation(projects.compose.foundation.preview)
    implementation(projects.compose.foundation.theme)
    implementation(projects.compose.foundation.uikit)

    implementation(projects.libraries.foundation)
    implementation(projects.libraries.resources)

    implementation(projects.shared.appComponent)

    implementation(libs.androidx.browser)

    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons)

    implementation(libs.decompose)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)
}