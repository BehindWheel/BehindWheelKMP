plugins {
    id("grodnoroads.kmplibrary")
}

android {
    namespace = "com.egoriku.grodnoroads.datastore"
}

kotlin {
    androidTarget()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "datastore"
            isStatic = true
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                api(libs.androidx.datastore.core)
            }
        }
        androidMain {
            dependencies {
                api(libs.androidx.datastore)
            }
        }
    }
}