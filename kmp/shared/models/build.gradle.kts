
import com.egoriku.grodnoroads.extension.applyTargets
import com.egoriku.grodnoroads.extension.commonDependencies

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.egoriku.grodnoroads.shared.models"
}

kotlin {
    applyTargets()

    sourceSets {
        commonDependencies {
            implementation(libs.kotlin.serialization.core)
        }
    }
}