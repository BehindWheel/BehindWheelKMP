@file:Suppress("unused")

import com.egoriku.grodnoroads.internal.applicationExtension
import com.egoriku.grodnoroads.internal.applicationPluginId
import com.egoriku.grodnoroads.internal.implementation
import com.egoriku.grodnoroads.internal.libraryExtension
import com.egoriku.grodnoroads.internal.libraryPluginId
import com.egoriku.grodnoroads.internal.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidComposePlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        pluginManager.withPlugin(applicationPluginId) {
            applicationExtension {
                buildFeatures {
                    compose = true
                }

                composeOptions {
                    kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
                }
                dependencies {
                    implementation(platform(libs.androidx.compose.bom))
                }
            }
        }
        pluginManager.withPlugin(libraryPluginId) {
            libraryExtension {
                buildFeatures {
                    compose = true
                }

                composeOptions {
                    kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
                }
                dependencies {
                    implementation(platform(libs.androidx.compose.bom))
                }
            }
        }
    }
}