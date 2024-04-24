import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.iosTarget

plugins {
    id("grodnoroads.kmplibrary")
    id("grodnoroads.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.quicksettings"
}

kotlin {
    androidTarget()
    iosTarget()

    sourceSets {
        commonDependencies {
            implementation(projects.kmp.shared.persistent)
            implementation(projects.kmp.libraries.coroutines)

            compileOnly(libs.compose.stable.marker)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.decompose)
            implementation(libs.kotlin.collections)
            implementation(libs.mvikotlin.extensions)
            implementation(libs.mvikotlin.main)
            implementation(libs.mvikotlin)
        }
        androidDependencies {
            implementation(projects.compose.foundation.uikit)
            implementation(projects.compose.commonUi)
            implementation(projects.libraries.resources)
        }
    }
}