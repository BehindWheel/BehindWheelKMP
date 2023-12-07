package com.egoriku.grodnoroads.location.di

import com.egoriku.grodnoroads.location.LocationHelper
import com.egoriku.grodnoroads.location.LocationHelperImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val locationModule = module {
    singleOf(::LocationHelperImpl) { bind<LocationHelper>() }
}