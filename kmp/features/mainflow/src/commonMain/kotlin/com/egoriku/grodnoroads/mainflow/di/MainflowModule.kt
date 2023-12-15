package com.egoriku.grodnoroads.mainflow.di

import com.egoriku.grodnoroads.settings.changelog.domain.di.changelogModule
import org.koin.dsl.module

val mainFlowModule = module {
    includes(changelogModule)
}