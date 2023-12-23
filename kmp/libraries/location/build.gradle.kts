import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.setupIosTarget

plugins {
    id("grodnoroads.kmplibrary")
}

android {
    namespace = "com.egoriku.grodnoroads.location"
}

kotlin {
    androidTarget()
    setupIosTarget(baseName = "location")

    sourceSets {
        androidDependencies {
            implementation(libs.play.services.maps)
        }
    }
}