plugins {
    id("grodnoroads.library")
}

android {
    namespace = "com.egoriku.grodnoroads.extensions"
}

dependencies {
    implementation(libs.androidx.appcompat)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.runtime)

    implementation(libs.coroutines)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.database)
    implementation(libs.firebase.firestore)

    implementation(libs.gms.maps)

    implementation(libs.maps.utils)
}