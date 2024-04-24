import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.iosTarget

plugins {
    id("grodnoroads.kmplibrary")
}

android {
    namespace = "com.egoriku.grodnoroads.shared.geolocation"
}

kotlin {
    androidTarget()
    iosTarget()

    sourceSets {
        commonDependencies {
            api(projects.kmp.libraries.location)
            implementation(projects.kmp.libraries.coroutines)
            implementation(projects.kmp.libraries.logger)

            compileOnly(libs.compose.stable.marker)
            implementation(libs.koin.core)
        }
        androidDependencies {
            implementation(libs.play.services.location)
            implementation(libs.kotlin.coroutines.playservices)
        }
    }
}