plugins {
    id("grodnoroads.library")
    id("grodnoroads.library.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.map"
}

dependencies {
    implementation(projects.features.map.mapDomain)

    implementation(projects.shared.appSettings)

    implementation(projects.libraries.extensions)
    implementation(projects.libraries.foundation)
    implementation(projects.libraries.resources)

    implementation(libs.accompanist.permissions)

    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(libs.gms.location)

    implementation(libs.immutable.collections)

    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    implementation(libs.maps)
    implementation(libs.maps.compose)
    implementation(libs.maps.utils)
}
