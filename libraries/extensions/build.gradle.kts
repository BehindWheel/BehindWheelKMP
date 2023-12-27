plugins {
    id("grodnoroads.library")
}

android {
    namespace = "com.egoriku.grodnoroads.extensions"
}

dependencies {
    implementation(projects.kmp.libraries.extensions)

    implementation(libs.androidx.appcompat)

    implementation(libs.kotlin.coroutines)
}