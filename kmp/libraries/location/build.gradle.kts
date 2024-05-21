import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.iosTarget

plugins {
    alias(libs.plugins.grodnoroads.kmplibrary)
}

android {
    namespace = "com.egoriku.grodnoroads.location"
}

kotlin {
    androidTarget()
    iosTarget()

    sourceSets {
        commonDependencies {
            compileOnly(libs.compose.stable.marker)
        }
        androidDependencies {
            implementation(libs.play.services.maps)
        }
    }
}