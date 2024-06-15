
import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.iosTarget

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
    alias(libs.plugins.moko.resources)
}

android {
    namespace = "com.egoriku.grodnoroads.shared.resources"
}

kotlin {
    androidTarget()
    iosTarget()

    sourceSets {
        commonDependencies {
            api(libs.moko.resources)
        }
    }
}

multiplatformResources {
    resourcesPackage = "com.egoriku.grodnoroads.shared.resources"
}