plugins {
    id("grodnoroads.library")
    id("grodnoroads.library.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.mapswrapper"
}

dependencies {
    implementation(projects.libraries.extensions)
    implementation(projects.libraries.mapsCore)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.foundation)

    implementation(libs.google.maps)
}