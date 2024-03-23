plugins {
    alias(libs.plugins.grodnoroads.library)
    alias(libs.plugins.grodnoroads.compose)
}

android {
    namespace = "com.egoriku.grodnoroads.eventreporting"
}

dependencies {
    implementation(projects.compose.foundation.core)
    implementation(projects.compose.foundation.preview)
    implementation(projects.compose.foundation.theme)
    implementation(projects.compose.foundation.uikit)

    implementation(projects.libraries.analytics)
    implementation(projects.libraries.resources)
    implementation(projects.shared.coreModels)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)

    implementation(libs.decompose)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.database)
    implementation(libs.google.maps.utils)

    implementation(libs.immutable.collections)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)
    implementation(libs.mvikotlin.extensions)
    implementation(libs.mvikotlin.main)
    implementation(libs.mvikotlin)
}