@file:Suppress("unused")

import com.android.build.gradle.LibraryExtension
import com.egoriku.grodnoroads.internal.configureKotlinAndroidToolchain
import com.egoriku.grodnoroads.internal.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class AndroidLibraryPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        apply(plugin = libs.plugins.kotlin.android.get().pluginId)
        apply(plugin = libs.plugins.android.library.get().pluginId)

        extensions.configure<LibraryExtension> {
            defaultConfig {
                minSdk = libs.versions.minSdk.get().toInt()
                compileSdk = libs.versions.compileSdk.get().toInt()

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

                consumerProguardFiles("consumer-rules.pro")
            }

            buildTypes {
                release {
                    isMinifyEnabled = true
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }

                debug {
                    isMinifyEnabled = false
                }
            }

            buildFeatures {
                buildConfig = false
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
        }

        configureKotlinAndroidToolchain()
    }
}