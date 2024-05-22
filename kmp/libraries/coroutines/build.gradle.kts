import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.iosTarget

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
}

android {
    namespace = "com.egoriku.grodnoroads.coroutines"
}

kotlin {
    androidTarget()
    iosTarget()

    sourceSets {
        commonDependencies {
            implementation(libs.decompose)
            api(libs.kotlin.coroutines)
        }
    }
}