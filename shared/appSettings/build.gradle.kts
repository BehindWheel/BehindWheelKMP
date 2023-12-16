plugins {
    id("grodnoroads.library")
}

android {
    namespace = "com.egoriku.grodnoroads.shared.appsettings"
}

dependencies {
    implementation(projects.libraries.resources)

    api(libs.androidx.datastore)
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.play.services.maps)
}