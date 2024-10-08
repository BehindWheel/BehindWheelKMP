import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.applyTargets
import com.egoriku.grodnoroads.extension.commonDependencies

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
}

android {
    namespace = "com.egoriku.grodnoroads.datastore"
}

kotlin {
    applyTargets()

    sourceSets {
        commonDependencies {
            api(libs.androidx.datastore.core)
        }
        androidDependencies {
            api(libs.androidx.datastore)
        }
    }
}