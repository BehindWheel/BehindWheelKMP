package com.egoriku.grodnoroads.setting.whatsnew.di

import com.egoriku.grodnoroads.setting.whatsnew.data.repository.WhatsNewRepositoryImpl
import com.egoriku.grodnoroads.setting.whatsnew.domain.repository.WhatsNewRepository
import com.egoriku.grodnoroads.setting.whatsnew.domain.store.WhatsNewStoreFactory
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