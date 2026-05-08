import com.egoriku.grodnoroads.extension.configureTargets

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
}

kotlin {
    configureTargets(namespace = "com.egoriku.grodnoroads.location")

    sourceSets {
        androidMain.dependencies {
            implementation(libs.play.services.maps)
        }
    }
}
