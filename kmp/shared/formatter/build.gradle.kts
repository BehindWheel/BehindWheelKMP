import com.egoriku.grodnoroads.extension.applyTargets
import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.commonTestDependencies

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
}

android {
    namespace = "com.egoriku.grodnoroads.shared.formatter"
}

kotlin {
    applyTargets()

    sourceSets {
        commonDependencies {
            implementation(libs.kotlin.datetime)
        }
        commonTestDependencies {
            implementation(libs.kotlin.test)
        }
    }
}