plugins {
    id("grodnoroads.library")
    id("grodnoroads.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.settings"
}

dependencies {
    implementation(projects.kmp.shared.components)
    implementation(projects.kmp.shared.models)
    implementation(projects.compose.foundation.preview)
    implementation(projects.compose.foundation.theme)
    implementation(projects.compose.foundation.uikit)
    implementation(projects.compose.commonUi)

    implementation(projects.libraries.resources)


    implementation(libs.androidx.browser)

    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons)

    implementation(libs.decompose)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)
}