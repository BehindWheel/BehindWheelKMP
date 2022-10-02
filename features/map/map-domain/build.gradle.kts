plugins {
    id("grodnoroads.library")
    id("grodnoroads.library.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.map.domain"
}

dependencies {
    implementation(projects.libraries.extensions)

    implementation(libs.coroutines)

    implementation(libs.decompose)

    implementation(libs.maps)

    implementation(libs.mvikotlin)
    implementation(libs.mvikotlin.extensions)
    implementation(libs.mvikotlin.main)
}
