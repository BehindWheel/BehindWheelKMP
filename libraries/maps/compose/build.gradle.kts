plugins {
    id("grodnoroads.library")
    id("grodnoroads.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.maps.compose"
}

dependencies {
    implementation(projects.libraries.extensions)
    implementation(projects.libraries.maps.core)

    implementation(libs.androidx.compose.foundation)

    implementation(libs.google.maps)
}