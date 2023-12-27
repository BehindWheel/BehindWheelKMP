import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.commonTestDependencies
import com.egoriku.grodnoroads.extension.setupIosTarget

plugins {
    id("grodnoroads.kmplibrary")
  //  id("grodnoroads.compose")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.egoriku.grodnoroads.guidance"
}

kotlin {
    androidTarget()
    setupIosTarget(baseName = "guidance")

    sourceSets {
        commonDependencies {
            implementation(projects.kmp.shared.geolocation)
            implementation(projects.kmp.shared.persistent)
            implementation(projects.kmp.libraries.coroutines)
            implementation(projects.kmp.libraries.crashlytics)
            implementation(projects.kmp.libraries.extensions)
            implementation(projects.kmp.libraries.logger)
            implementation(projects.kmp.libraries.uuid)

            implementation(libs.decompose)
            implementation(libs.dev.gitlive.firebase.database)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.kotlin.collections)
            implementation(libs.mvikotlin)
            implementation(libs.mvikotlin.extensions)
            implementation(libs.mvikotlin.main)
        }
        androidDependencies {
            implementation(projects.libraries.audioplayer)
        }
        commonTestDependencies {
            implementation(libs.kotlin.datetime)
            implementation(libs.kotlin.test)
        }
    }
}