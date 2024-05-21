import com.egoriku.grodnoroads.extension.iosTarget

plugins {
    alias(libs.plugins.grodnoroads.kmplibrary)
}

android {
    namespace = "com.egoriku.grodnoroads.uuid"
}

kotlin {
    androidTarget()
    iosTarget()
}