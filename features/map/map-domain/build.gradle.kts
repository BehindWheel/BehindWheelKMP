plugins {
    id("grodnoroads.library")
    id("grodnoroads.library.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.map.domain"
}

dependencies {
    implementation(projects.libraries.analytics)
    implementation(projects.libraries.audioplayer)
    implementation(projects.libraries.crashlytics)
    implementation(projects.libraries.extensions)
    implementation(projects.libraries.location)
    implementation(projects.libraries.maps.core)
    implementation(projects.libraries.resources)

    implementation(projects.shared.appSettings)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.runtime)

    implementation(libs.coroutines)
    implementation(libs.decompose)
    implementation(libs.google.maps.utils)
    implementation(libs.immutable.collections)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)

    implementation(libs.mvikotlin)
    implementation(libs.mvikotlin.extensions)
    implementation(libs.mvikotlin.main)
    implementation(libs.play.services.maps)

    testImplementation(libs.kotlin.test)
}
