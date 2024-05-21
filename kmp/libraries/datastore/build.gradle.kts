import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.iosTarget

plugins {
    alias(libs.plugins.grodnoroads.kmplibrary)
}

android {
    namespace = "com.egoriku.grodnoroads.datastore"
}

kotlin {
    androidTarget()
    iosTarget()

    sourceSets {
        commonDependencies {
            api(libs.androidx.datastore.core)
        }
        androidDependencies {
            api(libs.androidx.datastore)
        }
    }
}