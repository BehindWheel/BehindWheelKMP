package com.egoriku.grodnoroads.map.di

import com.egoriku.grodnoroads.map.util.MarkerCache
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val mapUiModule = module {
    singleOf(::MarkerCache)
   // singleOf(::SoundUtil)
}