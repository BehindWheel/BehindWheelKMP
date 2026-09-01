@file:Suppress("unused")

import com.egoriku.grodnoroads.internal.android
import com.egoriku.grodnoroads.internal.androidKmpLibraryPluginId
import com.egoriku.grodnoroads.internal.kotlin
import com.egoriku.grodnoroads.internal.kotlinMultiplatformPluginId
import com.egoriku.grodnoroads.internal.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

class AndroidMultiplatformLibraryPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        apply(plugin = androidKmpLibraryPluginId)
        apply(plugin = kotlinMultiplatformPluginId)

        kotlin {
            compilerOptions {
                freeCompilerArgs.addAll(
                    "-Xexpect-actual-classes",
                    "-Xwarning-level=REDUNDANT_VISIBILITY_MODIFIER:disabled",
                )
                extraWarnings.set(true)
            }

            android {
                minSdk = libs.versions.minSdk.get().toInt()
                compileSdk = libs.versions.compileSdk.get().toInt()

                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_17)
                }
            }

            sourceSets {
                all {
                    languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
                }
            }
        }
    }
}
