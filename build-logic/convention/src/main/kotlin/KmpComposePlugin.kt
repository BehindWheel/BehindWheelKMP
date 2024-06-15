@file:Suppress("unused")

import com.egoriku.grodnoroads.internal.composeCompiler
import com.egoriku.grodnoroads.internal.composeCompilerPluginId
import com.egoriku.grodnoroads.internal.jetbrainsComposePluginId
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.assign

class KmpComposePlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        apply(plugin = jetbrainsComposePluginId)
        apply(plugin = composeCompilerPluginId)

        composeCompiler {
            if (project.hasProperty("enableComposeCompilerReports")) {
                val outPath = project.layout.buildDirectory.dir("compose_metrics").get().asFile

                reportsDestination.set(outPath)
                metricsDestination.set(outPath)
            }
            stabilityConfigurationFile = file("$rootDir/config/compose-stability.config")
            includeSourceInformation = true
        }
    }
}