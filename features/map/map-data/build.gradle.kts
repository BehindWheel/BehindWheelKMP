plugins {
    id("grodnoroads.library")
}

android {
    namespace = "com.egoriku.grodnoroads.map.data"
}

dependencies {
    implementation(projects.features.map.mapDomain)
    implementation(projects.libraries.maps.core)
    implementation(projects.libraries.extensions)

    implementation(libs.coroutines)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.database)

    implementation(libs.immutable.collections)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)

    implementation(libs.play.services.maps)

    testImplementation(libs.kotlin.test)
}
