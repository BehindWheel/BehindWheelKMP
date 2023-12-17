import com.egoriku.grodnoroads.extension.setupIosTarget

plugins {
    id("grodnoroads.kmplibrary")
}

android {
    namespace = "com.egoriku.grodnoroads.shared.components"
}

kotlin {
    androidTarget()
    setupIosTarget(baseName = "components")
}