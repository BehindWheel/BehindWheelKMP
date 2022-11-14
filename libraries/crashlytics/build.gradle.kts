plugins {
    id("grodnoroads.library")
}

android {
    namespace = "com.egoriku.grodnoroads.crashlytics"
}

dependencies {
    implementation(projects.libraries.extensions)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)

    implementation(libs.koin.android)
}