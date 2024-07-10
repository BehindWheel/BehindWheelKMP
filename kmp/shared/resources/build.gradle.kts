import com.egoriku.grodnoroads.extension.applyTargets
import com.egoriku.grodnoroads.extension.commonDependencies

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
    alias(libs.plugins.moko.resources)
}

android {
    namespace = "com.egoriku.grodnoroads.shared.resources"
}

kotlin {
    applyTargets()

    sourceSets {
        commonDependencies {
            api(libs.moko.resources)
        }
    }
}

multiplatformResources {
    resourcesPackage = "com.egoriku.grodnoroads.shared.resources"
}