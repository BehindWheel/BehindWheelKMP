plugins {
    alias(libs.plugins.grodnoroads.library)
}

android {
    namespace = "com.egoriku.grodnoroads.analytics"
}

dependencies {
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)

    implementation(libs.androidx.core)
    implementation(libs.koin.android)
}