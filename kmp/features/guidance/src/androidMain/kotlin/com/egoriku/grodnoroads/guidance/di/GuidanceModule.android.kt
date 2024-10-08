package com.egoriku.grodnoroads.guidance.di

import com.egoriku.grodnoroads.guidance.screen.cache.MarkerCache
import com.egoriku.grodnoroads.guidance.screen.cache.MarkerCacheAndroid
import com.egoriku.grodnoroads.guidance.screen.sound.AndroidSoundController
import com.egoriku.grodnoroads.guidance.screen.sound.SoundController
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val guidancePlatformModule = module {
    singleOf(::AndroidSoundController) { bind<SoundController>() }
    singleOf(::MarkerCacheAndroid) { bind<MarkerCache>() }
}
