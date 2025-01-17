import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.applyTargets
import com.egoriku.grodnoroads.extension.commonDependencies

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
}

android {
    namespace = "com.egoriku.grodnoroads.foundation.core"
}

kotlin {
    applyTargets()

    sourceSets {
        commonDependencies {
            api(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(libs.material3.windowsize)
        }
        androidDependencies {
            implementation(libs.androidx.activity.compose)
        }
    }
}
