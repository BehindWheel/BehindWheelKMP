import com.egoriku.grodnoroads.extension.applyTargets
import com.egoriku.grodnoroads.extension.commonDependencies

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
}

android {
    namespace = "com.egoriku.grodnoroads.coroutines"
}

kotlin {
    applyTargets()

    sourceSets {
        commonDependencies {
            implementation(libs.decompose)
            api(libs.kotlin.coroutines)
        }
    }
}