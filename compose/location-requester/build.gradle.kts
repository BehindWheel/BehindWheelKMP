plugins {
    alias(libs.plugins.grodnoroads.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
}

android {
    namespace = "com.egoriku.grodnoroads.location.requester"
}

dependencies {
    implementation(projects.kmp.compose.foundation.core)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.kotlin.coroutines.playservices)
    implementation(libs.play.services.location)
    implementation(compose.material3)
    implementation(compose.runtime)
}