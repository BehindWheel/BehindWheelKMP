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

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.database)

    implementation(libs.koin.android)

    implementation(libs.gms.maps)

    implementation(libs.immutable.collections)

    testImplementation(libs.kotlin.tests)
}
