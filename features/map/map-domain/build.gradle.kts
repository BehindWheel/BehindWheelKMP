plugins {
    id("grodnoroads.library")
    id("grodnoroads.library.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.map.domain"
}

dependencies {
    implementation(projects.libraries.analytics)
    implementation(projects.libraries.crashlytics)
    implementation(projects.libraries.extensions)
    implementation(projects.libraries.location)
    implementation(projects.libraries.resources)

    implementation(projects.shared.appSettings)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.runtime)

    implementation(libs.coroutines)

    implementation(libs.decompose)

    implementation(libs.gms.maps)

    implementation(libs.maps.utils)

    implementation(libs.koin.android)

    implementation(libs.mvikotlin)
    implementation(libs.mvikotlin.extensions)
    implementation(libs.mvikotlin.main)
}
