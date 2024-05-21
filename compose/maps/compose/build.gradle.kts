plugins {
    alias(libs.plugins.grodnoroads.library)
    alias(libs.plugins.grodnoroads.compose)
}

android {
    namespace = "com.egoriku.grodnoroads.maps.compose"
}

dependencies {
    implementation(libs.androidx.compose.foundation)

    implementation(libs.google.maps)
    implementation(libs.google.maps.utils)
}