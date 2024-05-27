import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.applyTargets
import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.commonTestDependencies

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
}

android {
    namespace = "com.egoriku.grodnoroads.extensions"
}

kotlin {
    applyTargets()

    sourceSets {
        commonDependencies {
            implementation(libs.androidx.annotation)
            implementation(libs.kotlin.datetime)
        }
        androidDependencies {
            implementation(libs.androidx.appcompat)
        }
        commonTestDependencies {
            implementation(libs.kotlin.test)
        }
    }
}