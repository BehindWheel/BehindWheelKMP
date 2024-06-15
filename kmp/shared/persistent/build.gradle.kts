import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.iosTarget

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
}

android {
    namespace = "com.egoriku.grodnoroads.shared.persistent"
}

kotlin {
    androidTarget()
    iosTarget()

    sourceSets {
        commonDependencies {
            implementation(projects.kmp.compose.resources)
            implementation(projects.kmp.libraries.location)
            api(projects.kmp.libraries.datastore)

            implementation(compose.runtime)
        }
    }
}