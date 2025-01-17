import com.egoriku.grodnoroads.extension.applyTargets
import com.egoriku.grodnoroads.extension.commonDependencies

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
}

android {
    namespace = "com.egoriku.grodnoroads.compose.resources"
}

kotlin {
    applyTargets()

    sourceSets {
        commonDependencies {
            implementation(compose.runtime)
            api(compose.components.resources)
        }
    }
}

compose.resources {
    packageOfResClass = "com.egoriku.grodnoroads.compose.resources"
    generateResClass = always
    publicResClass = true
}
