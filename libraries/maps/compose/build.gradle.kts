plugins {
    id("grodnoroads.library")
    id("grodnoroads.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.maps.compose"
}

dependencies {
    implementation(projects.compose.maps.core)
    implementation(projects.libraries.extensions)

    implementation(libs.androidx.compose.foundation)

    implementation(libs.google.maps)
    implementation(libs.google.maps.utils)
}