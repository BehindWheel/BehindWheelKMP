import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.iosTarget

plugins {
    id("grodnoroads.kmplibrary")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.egoriku.grodnoroads.shared.models"
}

kotlin {
    androidTarget()
    iosTarget()

    sourceSets {
        commonDependencies {
            compileOnly(libs.compose.stable.marker)

            implementation(libs.kotlin.serialization.core)
        }
    }
}