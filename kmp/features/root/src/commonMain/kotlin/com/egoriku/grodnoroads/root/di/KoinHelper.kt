package com.egoriku.grodnoroads.root.di

import org.koin.core.module.Module

fun appModule() = listOf(platformDataStoreModule)

internal expect val platformDataStoreModule: Module
