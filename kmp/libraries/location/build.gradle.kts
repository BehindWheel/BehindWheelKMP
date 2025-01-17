
import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.applyTargets

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
}

android {
    namespace = "com.egoriku.grodnoroads.location"
}

kotlin {
    applyTargets()

    sourceSets {
        androidDependencies {
            implementation(libs.play.services.maps)
        }
    }
}
