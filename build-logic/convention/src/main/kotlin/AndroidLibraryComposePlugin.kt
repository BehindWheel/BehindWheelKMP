@file:Suppress("unused")

import com.android.build.gradle.LibraryExtension
import com.egoriku.grodnoroads.internal.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryComposePlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        extensions.configure<LibraryExtension> {
            buildFeatures {
                compose = true
            }

            composeOptions {
                kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
            }
        }
    }
}