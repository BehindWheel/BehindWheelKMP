import com.egoriku.grodnoroads.extension.configureTargets

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
    alias(libs.plugins.grodnoroads.kmp.compose)
}

kotlin {
    configureTargets(namespace = "com.egoriku.grodnoroads.shared.persistent")

    sourceSets {
        commonMain.dependencies {
            implementation(projects.kmp.compose.resources)
            implementation(projects.kmp.libraries.location)
            api(projects.kmp.libraries.datastore)

            implementation(libs.compose.runtime)
        }
    }
}
