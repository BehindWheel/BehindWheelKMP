import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import com.egoriku.grodnoroads.extension.iosTarget
import com.egoriku.grodnoroads.extension.provideVersionName

plugins {
    id("grodnoroads.kmplibrary")
    alias(libs.plugins.buildkonfig)
}

android {
    namespace = "com.egoriku.grodnoroads.shared.components"
}

kotlin {
    androidTarget()
    iosTarget()
}

buildkonfig {
    packageName = "com.egoriku.grodnoroads.shared"
    objectName = "AppBuildConfig"
    exposeObjectWithName = objectName

    defaultConfigs {
        buildConfigField(
            type = STRING,
            name = "versionName",
            value = provideVersionName("$rootDir/app/android/version.properties")
        )
    }
}
