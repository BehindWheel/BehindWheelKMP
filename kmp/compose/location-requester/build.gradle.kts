import com.egoriku.grodnoroads.extension.configureTargets

plugins {
    alias(libs.plugins.grodnoroads.multiplatform.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
}

kotlin {
    configureTargets(namespace = "com.egoriku.grodnoroads.location.requester")

    sourceSets {
        commonMain.dependencies {
            implementation(projects.kmp.compose.foundation.core)
            implementation(projects.kmp.libraries.extensions)

            implementation(libs.compose.material3)
            implementation(libs.compose.runtime)
        }
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.appcompat)
            implementation(libs.kotlin.coroutines.playservices)
            implementation(libs.play.services.location)
        }
    }
}
