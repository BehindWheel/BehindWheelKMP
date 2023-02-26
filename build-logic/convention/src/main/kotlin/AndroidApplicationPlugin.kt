@file:Suppress("unused")

import com.egoriku.grodnoroads.internal.configureKotlinAndroidToolchain
import com.egoriku.grodnoroads.internal.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class AndroidApplicationPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        apply(plugin = libs.plugins.kotlin.android.get().pluginId)
        apply(plugin = libs.plugins.android.application.get().pluginId)

        configureKotlinAndroidToolchain()
    }
}