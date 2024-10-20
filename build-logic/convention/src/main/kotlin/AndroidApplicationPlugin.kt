@file:Suppress("unused")

import com.egoriku.grodnoroads.internal.applicationExtension
import com.egoriku.grodnoroads.internal.applicationPluginId
import com.egoriku.grodnoroads.internal.configureKotlinAndroidToolchain
import com.egoriku.grodnoroads.internal.kotlinPluginId
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class AndroidApplicationPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        apply(plugin = applicationPluginId)
        apply(plugin = kotlinPluginId)

        applicationExtension {
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }

            buildTypes {
                release {
                    isMinifyEnabled = true
                    isShrinkResources = true
                    proguardFiles(
                        "proguard-rules.pro",
                        getDefaultProguardFile("proguard-android-optimize.txt")
                    )
                }
            }
        }
        configureKotlinAndroidToolchain()
    }
}
