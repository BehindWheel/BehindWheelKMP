plugins {
    alias(libs.plugins.grodnoroads.library)
}

android {
    namespace = "com.egoriku.grodnoroads.shared.appsettings"
}

dependencies {
    implementation(projects.libraries.resources)

    api(libs.androidx.datastore)
    implementation(libs.coroutines)
    implementation(libs.play.services.maps)
}