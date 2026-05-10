import com.egoriku.grodnoroads.extension.buildConfigField
import com.egoriku.grodnoroads.extension.configureTargets
import com.egoriku.grodnoroads.extension.ios
import com.egoriku.grodnoroads.extension.loadProperties
import com.egoriku.grodnoroads.extension.propertyString

plugins {
    alias(libs.plugins.grodnoroads.multiplatform.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
    alias(libs.plugins.kotlin.cocoapods)
    alias(libs.plugins.buildkonfig)
}

kotlin {
    configureTargets(namespace = "com.egoriku.grodnoroads.maps.compose")

    cocoapods {
        version = "1.0.0"
        ios.deploymentTarget = "15.0"

        podfile = project.file("../../../app/ios/Podfile")

        homepage = "https://github.com/grodnoroads/GrodnoRoads"
        summary = "GoogleMaps for Compose Multiplatform"

        pod("GoogleMaps") {
            version = "9.4.0"
            extraOpts += listOf("-compiler-option", "-fmodules")
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.kmp.compose.foundation.core)
            implementation(projects.kmp.compose.resources)
            implementation(projects.kmp.libraries.location)

            implementation(libs.compose.foundation)
        }
        androidMain.dependencies {
            implementation(libs.androidx.core)
            implementation(libs.google.maps)
            implementation(libs.google.maps.utils)
        }
    }
}

buildkonfig {
    packageName = "com.egoriku.grodnoroads.maps.compose"
    objectName = "MapsConfig"

    defaultConfigs {
        buildConfigField(name = "apiKey", value = "")
    }
    targetConfigs {
        ios {
            buildConfigField(
                name = "apiKey",
                value = loadProperties("$rootDir/secrets.properties").propertyString("MAPS_API_KEY_IOS")
            )
        }
    }
}
