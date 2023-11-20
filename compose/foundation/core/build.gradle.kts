plugins {
    id("grodnoroads.library")
    id("grodnoroads.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.foundation.core"
}

dependencies {
    implementation(libs.androidx.activity.compose)
}