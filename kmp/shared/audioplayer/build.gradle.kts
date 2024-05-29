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

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.components.resources)
        }
        androidDependencies {
            implementation(libs.androidx.core)
            implementation(libs.androidx.media)
        }
    }
}

compose.resources {
    packageOfResClass = "com.egoriku.grodnoroads.shared.audioplayer"
    generateResClass = always
}