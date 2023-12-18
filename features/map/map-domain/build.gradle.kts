plugins {
    id("grodnoroads.library")
    id("grodnoroads.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.map.domain"
}

dependencies {
    implementation(projects.kmp.libraries.crashlytics)
    implementation(projects.kmp.libraries.datastore)
    implementation(projects.kmp.libraries.extensions)

    implementation(projects.compose.maps.core)
    implementation(projects.libraries.analytics)
    implementation(projects.libraries.audioplayer)
    implementation(projects.libraries.extensions)
    implementation(projects.libraries.location)

    implementation(projects.shared.appSettings)

    implementation(libs.androidx.compose.runtime)

    implementation(libs.decompose)
    implementation(libs.google.maps.utils)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)

    implementation(libs.kotlin.collections)
    implementation(libs.kotlin.coroutines.core)

    implementation(libs.mvikotlin)
    implementation(libs.mvikotlin.extensions)
    implementation(libs.mvikotlin.main)
    implementation(libs.play.services.maps)

    testImplementation(libs.kotlin.test)
}
