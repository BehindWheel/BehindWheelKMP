package com.egoriku.grodnoroads.internal

import org.gradle.api.Project

context(Project)
internal val applicationPluginId
    get() = libs.plugins.android.application.get().pluginId

context(Project)
internal val kotlinPluginId
    get() = libs.plugins.kotlin.android.get().pluginId

context(Project)
internal val kotlinMultiplatformPluginId
    get() = libs.plugins.kotlin.multiplatform.get().pluginId

context(Project)
internal val libraryPluginId
    get() = libs.plugins.android.library.get().pluginId

context(Project)
internal val jetbrainsComposePluginId
    get() = libs.plugins.jetbrains.compose.get().pluginId

context(Project)
internal val composeCompilerPluginId
    get() = libs.plugins.compose.compiler.get().pluginId