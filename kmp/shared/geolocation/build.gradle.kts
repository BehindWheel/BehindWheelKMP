import com.egoriku.grodnoroads.extension.configureTargets

plugins {
    alias(libs.plugins.grodnoroads.multiplatform.library)
}

kotlin {
    configureTargets(namespace = "com.egoriku.grodnoroads.shared.geolocation")

    sourceSets {
        commonMain.dependencies {
            api(projects.kmp.libraries.location)
            implementation(projects.kmp.libraries.logger)

            implementation(libs.koin.core)
            implementation(libs.kotlin.coroutines)
        }
        androidMain.dependencies {
            implementation(libs.androidx.core)
            implementation(libs.play.services.location)
            implementation(libs.kotlin.coroutines.playservices)
        }
    }
}
