plugins {
    id("grodnoroads.library")
}

android {
    namespace = "com.egoriku.grodnoroads.shared.appsettings"
}

dependencies {
    implementation(projects.libraries.resources)

    api(libs.androidx.datastore)
    implementation(libs.gms.maps)
    implementation(libs.coroutines)
}