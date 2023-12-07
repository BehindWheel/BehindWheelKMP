plugins {
    id("grodnoroads.library")
    id("grodnoroads.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.map"
}

dependencies {
    implementation(projects.features.map.mapDomain)

    implementation(projects.compose.foundation.core)
    implementation(projects.compose.foundation.preview)
    implementation(projects.compose.foundation.theme)
    implementation(projects.compose.foundation.uikit)
    implementation(projects.compose.locationRequester)
    implementation(projects.compose.maps.core)
    implementation(projects.compose.maps.compose)
    implementation(projects.compose.snackbar)

    implementation(projects.libraries.extensions)
    implementation(projects.libraries.foundation)
    implementation(projects.libraries.resources)

    implementation(projects.shared.appComponent)
    implementation(projects.shared.appSettings)

    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons)

    implementation(libs.balloon.compose)
    implementation(libs.google.maps)
    implementation(libs.google.maps.utils)
    implementation(libs.immutable.collections)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
}
