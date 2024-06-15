@file:Suppress("unused")

import com.egoriku.grodnoroads.extension.compilerOptions
import com.egoriku.grodnoroads.internal.configureKotlinAndroidToolchain
import com.egoriku.grodnoroads.internal.kotlinExtension
import com.egoriku.grodnoroads.internal.kotlinPluginId
import com.egoriku.grodnoroads.internal.libraryExtension
import com.egoriku.grodnoroads.internal.libraryPluginId
import com.egoriku.grodnoroads.internal.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class AndroidLibraryPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        apply(plugin = libraryPluginId)
        apply(plugin = kotlinPluginId)

        libraryExtension {
            defaultConfig {
                minSdk = libs.versions.minSdk.get().toInt()
                compileSdk = libs.versions.compileSdk.get().toInt()

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

                consumerProguardFiles("consumer-rules.pro")
            }

            buildTypes {
                release {
                    isMinifyEnabled = false
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }
            }

            kotlinExtension {
                compilerOptions {
                    freeCompilerArgs.add("-Xcontext-receivers")
                }
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
        }

        configureKotlinAndroidToolchain()
    }
}