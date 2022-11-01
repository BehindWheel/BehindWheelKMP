package com.egoriku.grodnoroads.settings.appearance.di

import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponentImpl
import com.egoriku.grodnoroads.settings.appearance.domain.store.AppearanceStoreFactory
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val appearanceModule = module {
    factoryOf(::AppearanceComponentImpl) { bind<AppearanceComponent>() }

    factory {
        AppearanceStoreFactory(
            storeFactory = get(),
            dataStore = get()
        ).create()
    }
}