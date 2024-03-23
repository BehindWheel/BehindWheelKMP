plugins {
    alias(libs.plugins.grodnoroads.library)
}

android {
    namespace = "com.egoriku.grodnoroads.audioplayer"
}

dependencies {
    implementation(projects.libraries.extensions)

    implementation(libs.androidx.core)
    implementation(libs.androidx.media)
}