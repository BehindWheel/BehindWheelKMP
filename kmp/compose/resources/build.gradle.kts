import com.egoriku.grodnoroads.extension.configureTargets

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
}

kotlin {
    configureTargets(namespace = "com.egoriku.grodnoroads.compose.resources")

    android {
        androidResources {
            enable = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            api(libs.compose.resources)
        }
    }
}

compose.resources {
    packageOfResClass = "com.egoriku.grodnoroads.compose.resources"
    generateResClass = always
    publicResClass = true
}
