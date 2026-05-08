@file:Suppress("unused")

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import com.egoriku.grodnoroads.internal.androidKmpLibraryPluginId
import com.egoriku.grodnoroads.internal.kmpExtension
import com.egoriku.grodnoroads.internal.kotlinMultiplatformPluginId
import com.egoriku.grodnoroads.internal.libs
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.apply
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

class AndroidKmpLibraryPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        apply(plugin = androidKmpLibraryPluginId)
        apply(plugin = kotlinMultiplatformPluginId)

        kmpExtension {
            compilerOptions {
                freeCompilerArgs.addAll(
                    "-Xexpect-actual-classes",
                    "-Xwarning-level=REDUNDANT_VISIBILITY_MODIFIER:disabled"
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

fun KotlinMultiplatformExtension.android(
    configure: Action<KotlinMultiplatformAndroidLibraryTarget>
) = (this as ExtensionAware).extensions.configure("android", configure)

fun KotlinMultiplatformExtension.sourceSets(
    configure: Action<NamedDomainObjectContainer<KotlinSourceSet>>
): Unit = (this as ExtensionAware).extensions.configure("sourceSets", configure)

