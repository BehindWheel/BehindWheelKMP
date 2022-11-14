package com.egoriku.grodnoroads.screen.settings.whatsnew.di

import com.egoriku.grodnoroads.screen.settings.whatsnew.data.WhatsNewRepository
import com.egoriku.grodnoroads.screen.settings.whatsnew.data.WhatsNewRepositoryImpl
import com.egoriku.grodnoroads.screen.settings.whatsnew.store.WhatsNewStoreFactory
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val whatsNewModule = module {
    factoryOf(::WhatsNewRepositoryImpl) { bind<WhatsNewRepository>() }

    factory {
        WhatsNewStoreFactory(
            storeFactory = get(),
            whatsNewRepository = get(),
            crashlyticsTracker = get()
        ).create()
    }
}