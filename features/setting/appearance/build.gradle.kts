plugins {
    id("grodnoroads.library")
    id("grodnoroads.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.setting.appearance"
}

dependencies {
    implementation(projects.compose.foundation.core)
    implementation(projects.compose.foundation.theme)

    implementation(projects.libraries.foundation)
    implementation(projects.libraries.resources)

    implementation(projects.shared.appSettings)

    implementation(libs.androidx.appcompat)

    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(libs.decompose)
    implementation(libs.immutable.collections)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)

    implementation(libs.mvikotlin)
    implementation(libs.mvikotlin.extensions)
    implementation(libs.mvikotlin.main)
}