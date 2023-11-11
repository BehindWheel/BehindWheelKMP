package com.egoriku.grodnoroads.internal

import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.provider.Provider

internal fun DependencyHandler.implementation(provider: Provider<MinimalExternalModuleDependency>): Dependency? =
    add("implementation", provider)