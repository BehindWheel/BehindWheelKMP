import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.applyTargets
import com.egoriku.grodnoroads.extension.commonDependencies

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
}

android {
    namespace = "com.egoriku.grodnoroads.foundation.uikit"
}

kotlin {
    applyTargets()

    sourceSets {
        commonDependencies {
            implementation(projects.kmp.shared.resources)

            api(projects.kmp.compose.foundation.core)
            api(projects.kmp.compose.foundation.preview)

            implementation(compose.components.resources)
            api(compose.foundation)
            api(compose.material3)
            api(compose.runtime)
            api(compose.ui)
        }
        androidDependencies {
            api(compose.preview)
            api(compose.uiTooling)
        }
    }
}
