import com.egoriku.grodnoroads.extension.configureTargets

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
}

kotlin {
    configureTargets(namespace = "com.egoriku.grodnoroads.shared.components")

    sourceSets {
        androidMain.dependencies {
            implementation(libs.koin.core)
        }
    }
}
