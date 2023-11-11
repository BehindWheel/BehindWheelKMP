plugins {
    id("grodnoroads.library")
    id("grodnoroads.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.maps.core"
}

dependencies {
    implementation(libs.androidx.compose.foundation)

    implementation(libs.google.maps)
    implementation(libs.google.maps.utils)
}