plugins {
    id("grodnoroads.library")
    id("grodnoroads.library.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.setting.debugtools"
}

dependencies {
    implementation(projects.libraries.foundation)
    implementation(projects.libraries.maps.core)
    implementation(projects.libraries.maps.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(libs.immutable.collections)
    implementation(libs.google.maps)
    implementation(libs.google.maps)

}