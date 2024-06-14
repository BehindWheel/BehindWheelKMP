import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.applyTargets
import com.egoriku.grodnoroads.extension.commonDependencies

plugins {
    alias(libs.plugins.grodnoroads.kmp.compose)
    alias(libs.plugins.grodnoroads.kmp.library)
}

android {
    namespace = "com.egoriku.grodnoroads.shared.audioplayer"
}

kotlin {
    applyTargets()

    sourceSets {
        commonDependencies {
            implementation(projects.kmp.libraries.logger)
            implementation(projects.kmp.shared.resources)

            implementation(compose.runtime)
            implementation(compose.foundation)
        }
        androidDependencies {
            implementation(libs.androidx.core)
            implementation(libs.androidx.media)
        }
    }
}