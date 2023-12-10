plugins {
    id("grodnoroads.kmplibrary")
}

android {
    namespace = "com.egoriku.grodnoroads.root"
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "root"
            isStatic = true
        }
    }

    sourceSets {
        commonMain {
            dependencies {

            }
        }
    }
}