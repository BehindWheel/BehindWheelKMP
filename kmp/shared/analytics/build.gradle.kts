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
            implementation(libs.dev.gitlive.firebase.analytics)
            implementation(libs.koin.core)
        }
    }
}
