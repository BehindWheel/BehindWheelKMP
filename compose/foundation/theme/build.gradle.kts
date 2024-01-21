plugins {
    id("grodnoroads.library")
    id("grodnoroads.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.foundation.theme"
}

dependencies {
    implementation(libs.androidx.compose.material3)
}