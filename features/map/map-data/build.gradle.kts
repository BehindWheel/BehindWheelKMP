plugins {
    id("grodnoroads.library")
}

android {
    namespace = "com.egoriku.grodnoroads.map.data"
}

dependencies {
    implementation(projects.kmp.libraries.extensions)

    implementation(projects.compose.maps.core)
    implementation(projects.features.map.mapDomain)
    implementation(projects.libraries.extensions)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.database)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)

    implementation(libs.kotlin.collections)
    implementation(libs.kotlin.coroutines.core)

    implementation(libs.play.services.maps)

    testImplementation(libs.kotlin.test)
}
