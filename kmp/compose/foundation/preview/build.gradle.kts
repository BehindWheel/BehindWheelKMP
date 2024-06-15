import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.applyTargets
import com.egoriku.grodnoroads.extension.commonDependencies

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
}

android {
    namespace = "com.egoriku.grodnoroads.foundation.preview"
}

kotlin {
    applyTargets()

    sourceSets {
        commonDependencies {
            api(projects.kmp.compose.foundation.theme)

            api(compose.components.uiToolingPreview)
            implementation(compose.material3)
        }
        androidDependencies {
            api(compose.preview)
            api(compose.uiTooling)
        }
    }
}