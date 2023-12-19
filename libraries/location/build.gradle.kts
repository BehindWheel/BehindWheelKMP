plugins {
    id("grodnoroads.library")
}

android {
    namespace = "com.egoriku.grodnoroads.location"
}

dependencies {
    implementation(projects.kmp.libraries.logger)

    implementation(projects.libraries.extensions)

    implementation(libs.google.maps)
    
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)

    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin.coroutines.playservices)

    implementation(libs.play.services.location)
}