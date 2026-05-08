import com.egoriku.grodnoroads.extension.configureTargets

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
}

kotlin {
    configureTargets(namespace = "com.egoriku.grodnoroads.suntime")

    android {
        withHostTest {}
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlin.datetime)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
