import com.egoriku.grodnoroads.extension.configureTargets

plugins {
    alias(libs.plugins.grodnoroads.multiplatform.library)
}

kotlin {
    configureTargets(namespace = "com.egoriku.grodnoroads.shared.crashlytics")

    sourceSets {
        commonMain.dependencies {
            implementation(projects.kmp.libraries.logger)

            implementation(libs.dev.gitlive.firebase.crashlytics)
            implementation(libs.koin.core)
        }
        androidMain.dependencies {
            implementation(project.dependencies.platform(libs.firebase.bom))
            api(libs.firebase.crashlytics)
        }
    }
}
