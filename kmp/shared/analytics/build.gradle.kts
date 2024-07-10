import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.applyTargets
import com.egoriku.grodnoroads.extension.commonDependencies

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
}

android {
    namespace = "com.egoriku.grodnoroads.shared.analytics"
}

kotlin {
    applyTargets()

    sourceSets {
        commonDependencies {
            implementation(libs.koin.core)
        }
        androidDependencies {
            implementation(libs.androidx.core)
            implementation(libs.firebase.analytics)
            implementation(libs.koin.android)
        }
    }
}