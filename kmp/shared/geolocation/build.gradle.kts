import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.applyTargets
import com.egoriku.grodnoroads.extension.commonDependencies

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
}

android {
    namespace = "com.egoriku.grodnoroads.shared.geolocation"
}

kotlin {
    applyTargets()

    sourceSets {
        commonDependencies {
            api(projects.kmp.libraries.location)
            implementation(projects.kmp.libraries.coroutines)
            implementation(projects.kmp.libraries.logger)

            implementation(libs.koin.core)
        }
        androidDependencies {
            implementation(libs.play.services.location)
            implementation(libs.kotlin.coroutines.playservices)
        }
    }
}
