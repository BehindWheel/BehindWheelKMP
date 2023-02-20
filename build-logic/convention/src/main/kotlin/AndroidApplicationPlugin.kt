@file:Suppress("unused")

import com.egoriku.grodnoroads.internal.configureKotlinAndroidToolchain
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidApplicationPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.android.application")
            apply("org.jetbrains.kotlin.android")
        }

        configureKotlinAndroidToolchain()
    }
}