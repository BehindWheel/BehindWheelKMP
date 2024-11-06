package com.egoriku.grodnoroads.intro.di

import com.egoriku.grodnoroads.cityselector.di.citySelectorModule
import org.koin.dsl.module

val introModule = module {
    includes(citySelectorModule)
}
