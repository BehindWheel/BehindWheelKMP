import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.setupIosTarget

plugins {
    id("grodnoroads.kmplibrary")
}

android {
    namespace = "com.egoriku.grodnoroads.shared.persistent"
}

kotlin {
    androidTarget()
    setupIosTarget(baseName = "shared_persistent")

    sourceSets {
        commonDependencies {
            implementation(projects.kmp.libraries.datastore)
        }
        androidDependencies {
            implementation(projects.libraries.resources)
        }
    }
}