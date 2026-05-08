import com.egoriku.grodnoroads.extension.configureTargets

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    configureTargets(namespace = "com.egoriku.grodnoroads.shared.models")

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlin.serialization.core)
        }
    }
}
