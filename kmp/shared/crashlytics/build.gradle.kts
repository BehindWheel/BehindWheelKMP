import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.iosTarget

plugins {
    id("grodnoroads.kmplibrary")
}

android {
    namespace = "com.egoriku.grodnoroads.shared.crashlytics"
}

kotlin {
    androidTarget()
    iosTarget()

    sourceSets {
        commonDependencies {
            implementation(projects.kmp.libraries.logger)

            implementation(libs.dev.gitlive.firebase.crashlytics)
            implementation(libs.koin.core)
        }
    }
}