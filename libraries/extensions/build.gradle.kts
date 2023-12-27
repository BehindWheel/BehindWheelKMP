plugins {
    id("grodnoroads.library")
}

android {
    namespace = "com.egoriku.grodnoroads.extensions"
}

dependencies {
    implementation(projects.kmp.libraries.extensions)

    implementation(libs.androidx.appcompat)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.database)
    implementation(libs.firebase.firestore)

    implementation(libs.kotlin.coroutines)
}