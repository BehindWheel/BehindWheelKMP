package com.egoriku.grodnoroads.internal

import org.gradle.api.Project

internal val Project.applicationPluginId
    get() = libs.plugins.android.application.get().pluginId

internal val Project.kotlinMultiplatformPluginId
    get() = libs.plugins.kotlin.multiplatform.get().pluginId

internal val Project.androidKmpLibraryPluginId
    get() = libs.plugins.android.kmp.library.get().pluginId

internal val Project.jetbrainsComposePluginId
    get() = libs.plugins.jetbrains.compose.get().pluginId

internal val Project.composeCompilerPluginId
    get() = libs.plugins.compose.compiler.get().pluginId
