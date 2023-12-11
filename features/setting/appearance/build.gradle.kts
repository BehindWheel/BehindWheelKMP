plugins {
    id("grodnoroads.library")
    id("grodnoroads.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.setting.appearance"
}

dependencies {
    implementation(projects.compose.foundation.core)
    implementation(projects.compose.foundation.preview)
    implementation(projects.compose.foundation.theme)

    implementation(projects.compose.commonUi)

    implementation(projects.libraries.resources)

    implementation(projects.shared.appSettings)

    implementation(libs.androidx.appcompat)

    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons)

    implementation(libs.decompose)
    implementation(libs.immutable.collections)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)

    implementation(libs.mvikotlin)
    implementation(libs.mvikotlin.extensions)
    implementation(libs.mvikotlin.main)
}