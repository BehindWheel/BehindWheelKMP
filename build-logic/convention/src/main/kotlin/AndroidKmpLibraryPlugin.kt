@file:Suppress("unused")

import com.egoriku.grodnoroads.internal.kmpExtension
import com.egoriku.grodnoroads.internal.kotlinMultiplatformPluginId
import com.egoriku.grodnoroads.internal.libraryExtension
import com.egoriku.grodnoroads.internal.libraryPluginId
import com.egoriku.grodnoroads.internal.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

class AndroidKmpLibraryPlugin : Plugin<Project> {

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    override fun apply(target: Project) = with(target) {
        apply(plugin = libraryPluginId)
        apply(plugin = kotlinMultiplatformPluginId)

        kmpExtension {
            compilerOptions {
                freeCompilerArgs.addAll(
                    "-Xcontext-receivers",
                    "-Xexpect-actual-classes"
                )
            }
        }

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

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
        }
    }
}