@file:Suppress("unused")

import com.android.build.api.dsl.ApplicationExtension
import com.egoriku.grodnoroads.extension.kotlinOptions
import com.egoriku.grodnoroads.internal.configureKotlinAndroidToolchain
import com.egoriku.grodnoroads.internal.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

class AndroidApplicationPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        apply(plugin = libs.plugins.kotlin.android.get().pluginId)
        apply(plugin = libs.plugins.android.application.get().pluginId)

        extensions.configure<ApplicationExtension> {
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }

            kotlinOptions {
                freeCompilerArgs = listOf("-Xcontext-receivers")
                languageVersion = KotlinVersion.KOTLIN_1_9.version
            }
        }
        configureKotlinAndroidToolchain()
    }
}