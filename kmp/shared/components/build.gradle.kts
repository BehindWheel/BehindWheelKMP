import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.applyTargets

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
}

android {
    namespace = "com.egoriku.grodnoroads.shared.components"
}

kotlin {
    applyTargets()

    dependencies {
        androidDependencies {
            implementation(libs.koin.core)
        }
    }
}
