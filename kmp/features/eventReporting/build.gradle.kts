import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.iosTarget

plugins {
    id("grodnoroads.kmplibrary")
    id("grodnoroads.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.eventreporting"
}

kotlin {
    androidTarget()
    iosTarget()

    sourceSets {
        commonDependencies {
            implementation(projects.kmp.shared.models)

            implementation(projects.kmp.libraries.coroutines)
            implementation(projects.kmp.libraries.extensions)
            implementation(projects.kmp.libraries.location)

            compileOnly(libs.compose.stable.marker)

            implementation(libs.decompose)
            implementation(libs.dev.gitlive.firebase.database)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.kotlin.collections)
            implementation(libs.kotlin.coroutines)
            implementation(libs.mvikotlin)
            implementation(libs.mvikotlin.extensions)
            implementation(libs.mvikotlin.main)
        }
        androidDependencies {
            implementation(projects.compose.foundation.uikit)
            implementation(projects.libraries.resources)

            implementation(libs.androidx.activity.compose)
        }
    }
}