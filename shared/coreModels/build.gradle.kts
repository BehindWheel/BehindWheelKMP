plugins {
    alias(libs.plugins.grodnoroads.library)
}

android {
    namespace = "com.egoriku.grodnoroads.shared.core.models"
}

dependencies {
    implementation(libs.androidx.annotation)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.database)

    compileOnly(libs.compose.stable.marker)
}