import com.egoriku.grodnoroads.extension.configureTargets

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
}

kotlin {
    configureTargets(namespace = "com.egoriku.grodnoroads.datastore")

    sourceSets {
        commonMain.dependencies {
            api(libs.androidx.datastore.core)
        }
        androidMain.dependencies {
            api(libs.androidx.datastore)
        }
    }
}
