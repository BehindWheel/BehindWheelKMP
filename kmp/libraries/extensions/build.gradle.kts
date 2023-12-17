import com.egoriku.grodnoroads.extension.setupIosTarget

plugins {
    id("grodnoroads.kmplibrary")
}

android {
    namespace = "com.egoriku.grodnoroads.extensions"
}

kotlin {
    androidTarget()
    setupIosTarget(baseName = "extensions")
}