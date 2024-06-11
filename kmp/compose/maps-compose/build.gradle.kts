import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.applyTargets
import com.egoriku.grodnoroads.extension.commonDependencies

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
    alias(libs.plugins.kotlin.cocoapods)
}

android {
    namespace = "com.egoriku.grodnoroads.maps.compose"
}

kotlin {
    applyTargets()

    cocoapods {
        version = "1.0.0"
        ios.deploymentTarget = "15.0"

        podfile = project.file("../../../app/ios/Podfile")

        homepage = "https://github.com/grodnoroads/GrodnoRoads"
        summary = "Shared functionality for iOS"

        pod("GoogleMaps") {
            extraOpts += listOf("-compiler-option", "-fmodules")
        }
    }

    sourceSets {
        commonDependencies {
            implementation(projects.kmp.compose.resources)
            implementation(projects.kmp.libraries.location)

            implementation(compose.foundation)
        }
        androidDependencies {
            implementation(libs.androidx.core)
            implementation(libs.google.maps)
            implementation(libs.google.maps.utils)
        }
    }
}