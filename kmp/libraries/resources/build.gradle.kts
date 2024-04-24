import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.iosTarget

plugins {
    alias(libs.plugins.grodnoroads.kmplibrary)
    alias(libs.plugins.grodnoroads.compose)
    alias(libs.plugins.moko.resources)
}

android {
    namespace = "com.egoriku.grodnoroads.resources"
}

kotlin {
    androidTarget()
    iosTarget()

    sourceSets {
        commonDependencies {
            api(libs.moko.resources)
        }
        androidDependencies {
            implementation(libs.androidx.compose.ui)
            implementation(libs.moko.resources.compose)
        }
    }
}

multiplatformResources {
    resourcesPackage = "com.egoriku.grodnoroads.resources"
}