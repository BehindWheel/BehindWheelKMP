import com.egoriku.grodnoroads.extension.buildConfigField
import com.egoriku.grodnoroads.extension.configureTargets
import com.egoriku.grodnoroads.extension.ios
import com.egoriku.grodnoroads.extension.loadProperties
import com.egoriku.grodnoroads.extension.propertyString
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.grodnoroads.multiplatform.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
    alias(libs.plugins.buildkonfig)
}

kotlin {
    configureTargets(namespace = "com.egoriku.grodnoroads.maps.compose")

    swiftPMDependencies {
        iosMinimumDeploymentTarget =
            libs.versions.ios.minTarget
                .get()

        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        swiftPackage(
            url = url("https://github.com/googlemaps/ios-maps-sdk.git"),
            version =
                exact(
                    libs.versions.spm.googleMaps
                        .get()
                ),
            products = listOf(product("GoogleMaps"))
        )
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
