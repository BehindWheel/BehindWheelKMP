plugins {
    alias(libs.plugins.grodnoroads.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
}

android {
    namespace = "com.egoriku.grodnoroads.maps.compose"
}

dependencies {
    implementation(compose.foundation)

    implementation(libs.google.maps)
    implementation(libs.google.maps.utils)
}