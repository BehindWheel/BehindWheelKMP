import com.egoriku.grodnoroads.extension.configureTargets

plugins {
    alias(libs.plugins.grodnoroads.multiplatform.library)
}

kotlin {
    configureTargets(namespace = "com.egoriku.grodnoroads.auth")

    sourceSets {
        commonMain.dependencies {
            implementation(projects.kmp.libraries.extensions)

            implementation(libs.dev.gitlive.firebase.auth)
            implementation(libs.kotlin.coroutines)
            implementation(libs.koin.core)
        }
        androidMain.dependencies {
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.auth)
        }
    }
}
