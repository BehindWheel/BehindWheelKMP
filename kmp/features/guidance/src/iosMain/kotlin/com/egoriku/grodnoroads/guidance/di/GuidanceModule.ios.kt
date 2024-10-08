package com.egoriku.grodnoroads.guidance.di

import com.egoriku.grodnoroads.guidance.screen.cache.MarkerCache
import com.egoriku.grodnoroads.guidance.screen.cache.MarkerCacheIos
import com.egoriku.grodnoroads.guidance.screen.sound.IosSoundController
import com.egoriku.grodnoroads.guidance.screen.sound.SoundController
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val guidancePlatformModule = module {
    singleOf(::IosSoundController) { bind<SoundController>() }
    singleOf(::MarkerCacheIos) { bind<MarkerCache>() }
}