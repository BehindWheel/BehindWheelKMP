plugins {
    id("grodnoroads.library")
    id("grodnoroads.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.map"
}

dependencies {
    implementation(projects.features.map.mapDomain)

    implementation(projects.shared.appComponent)
    implementation(projects.shared.appSettings)

    implementation(projects.libraries.extensions)
    implementation(projects.libraries.foundation)
    implementation(projects.libraries.foundationCore)
    implementation(projects.libraries.locationPermissions)
    implementation(projects.libraries.maps.core)
    implementation(projects.libraries.maps.compose)
    implementation(projects.libraries.resources)

    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(libs.balloon.compose)
    implementation(libs.google.maps)
    implementation(libs.google.maps.utils)
    implementation(libs.immutable.collections)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
}
