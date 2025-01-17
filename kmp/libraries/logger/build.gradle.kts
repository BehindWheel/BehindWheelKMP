import com.egoriku.grodnoroads.extension.applyTargets

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
}

android {
    namespace = "com.egoriku.grodnoroads.logger"
}

kotlin {
    applyTargets()
}
