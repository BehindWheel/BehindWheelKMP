import com.egoriku.grodnoroads.extension.android
import com.egoriku.grodnoroads.extension.applyTargets
import com.egoriku.grodnoroads.extension.buildConfigField
import com.egoriku.grodnoroads.extension.ios
import com.egoriku.grodnoroads.extension.provideVersionName

plugins {
    alias(libs.plugins.grodnoroads.kmp.library)
    alias(libs.plugins.buildkonfig)
}

android {
    namespace = "com.egoriku.grodnoroads.shared.components"

    buildFeatures {
        buildConfig = true
    }
}

kotlin {
    applyTargets()
}

buildkonfig {
    packageName = "com.egoriku.grodnoroads.shared"
    objectName = "AppBuildConfig"
    exposeObjectWithName = objectName

    defaultConfigs {
        buildConfigField(name = "versionName", value = "0.0.0")
    }

    val iosVersion = provideVersionName("$rootDir/config/versioning/ios.properties")
    val androidVersion = provideVersionName("$rootDir/config/versioning/android.properties")

    targetConfigs {
        android {
            buildConfigField(name = "versionName", value = androidVersion)
        }
        ios {
            buildConfigField(name = "versionName", value = iosVersion)
        }
    }
}
