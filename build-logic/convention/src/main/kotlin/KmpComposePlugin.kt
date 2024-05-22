@file:Suppress("unused")

import com.egoriku.grodnoroads.internal.composeCompilerPluginId
import com.egoriku.grodnoroads.internal.jetbrainsComposePluginId
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class KmpComposePlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        apply(plugin = jetbrainsComposePluginId)
        apply(plugin = composeCompilerPluginId)
    }
}