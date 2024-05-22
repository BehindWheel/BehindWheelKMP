import com.egoriku.grodnoroads.extension.iosTarget

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
}

android {
    namespace = "com.egoriku.grodnoroads.logger"
}

kotlin {
    androidTarget()
    iosTarget()
}