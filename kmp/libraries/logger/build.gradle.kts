import com.egoriku.grodnoroads.extension.configureTargets

plugins {
    alias(libs.plugins.grodnoroads.multiplatform.library)
}

kotlin {
    configureTargets(namespace = "com.egoriku.grodnoroads.logger")
}
