import com.egoriku.grodnoroads.extension.configureTargets

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
}

kotlin {
    configureTargets(namespace = "com.egoriku.grodnoroads.shared.analytics")

    sourceSets {
        commonMain.dependencies {
            implementation(libs.dev.gitlive.firebase.analytics)
            implementation(libs.koin.core)
        }
        androidMain.dependencies {
            implementation(project.dependencies.platform(libs.firebase.bom))
            api(libs.firebase.analytics)
        }
    }
}
