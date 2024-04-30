import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import com.codingfeline.buildkonfig.gradle.TargetConfigDsl
import com.egoriku.grodnoroads.extension.iosTarget
import com.egoriku.grodnoroads.extension.provideVersionName

plugins {
    alias(libs.plugins.grodnoroads.kmplibrary)
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

fun NamedDomainObjectContainer<TargetConfigDsl>.ios(block: TargetConfigDsl.() -> Unit) {
    listOf("iosArm64", "iosSimulatorArm64", "iosX64").forEach {
        create(it, block)
    }
}

fun NamedDomainObjectContainer<TargetConfigDsl>.android(block: TargetConfigDsl.() -> Unit) {
    create("android", block)
}

fun TargetConfigDsl.buildConfigField(name: String, value: String) {
    buildConfigField(type = STRING, name = name, value = value, const = true)
}