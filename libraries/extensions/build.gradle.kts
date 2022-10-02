plugins {
    id("grodnoroads.library")
}

android {
    namespace = "com.egoriku.grodnoroads.extensions"
}

dependencies {
    implementation(libs.coroutines)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.database)
    implementation(libs.firebase.firestore)

    implementation(libs.maps)
    implementation(libs.maps.utils)
}