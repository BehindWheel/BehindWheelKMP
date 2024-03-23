plugins {
    alias(libs.plugins.grodnoroads.library)
}

android {
    namespace = "com.egoriku.grodnoroads.crashlytics"
}

dependencies {
    implementation(projects.libraries.extensions)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)
}