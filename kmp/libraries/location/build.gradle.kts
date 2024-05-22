import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.iosTarget

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
}

android {
    namespace = "com.egoriku.grodnoroads.location"
}

kotlin {
    androidTarget()
    iosTarget()

    sourceSets {
        androidDependencies {
            implementation(libs.play.services.maps)
        }
    }
}