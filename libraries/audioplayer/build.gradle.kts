plugins {
    id("grodnoroads.library")
}

android {
    namespace = "com.egoriku.grodnoroads.audioplayer"
}

dependencies {
    implementation(projects.kmp.libraries.logger)

    implementation(projects.libraries.extensions)

    implementation(libs.androidx.core)
    implementation(libs.androidx.media)
}