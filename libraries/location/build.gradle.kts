plugins {
    alias(libs.plugins.grodnoroads.library)
}

android {
    namespace = "com.egoriku.grodnoroads.location"
}

dependencies {
    implementation(projects.libraries.extensions)

    implementation(libs.coroutines)
    implementation(libs.coroutines.playservices)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)

    implementation(libs.google.maps)
    implementation(libs.play.services.location)
}