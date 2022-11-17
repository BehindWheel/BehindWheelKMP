plugins {
    id("grodnoroads.library")
    id("grodnoroads.library.compose")
    id("kotlin-parcelize")
}

android {
    namespace = "com.egoriku.grodnoroads.settings.root"
}

dependencies {
    implementation(projects.features.settings.alerts)
    implementation(projects.features.settings.appearance)
    implementation(projects.features.settings.faq)
    implementation(projects.features.settings.map)
    implementation(projects.features.settings.whatsnew)

    implementation(projects.libraries.foundation)
    implementation(projects.libraries.resources)

    implementation(projects.shared.appComponent)

    implementation(libs.androidx.browser)

    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(libs.androidx.compose.material.icons)

    implementation(libs.decompose)
    implementation(libs.decompose.compose.jetpack)

    implementation(libs.koin.android)

    implementation(libs.mvikotlin)
    implementation(libs.mvikotlin.extensions)
    implementation(libs.mvikotlin.main)
}