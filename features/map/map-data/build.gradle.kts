plugins {
    id("grodnoroads.library")
}

android {
    namespace = "com.egoriku.grodnoroads.map.data"
}

dependencies {
    implementation(projects.features.map.mapDomain)
    implementation(projects.libraries.extensions)

    implementation(libs.coroutines)
    implementation(libs.coroutines.playservices)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.database)

    implementation(libs.koin.android)

    implementation(libs.maps)

    testImplementation(libs.kotlin.tests)
}
