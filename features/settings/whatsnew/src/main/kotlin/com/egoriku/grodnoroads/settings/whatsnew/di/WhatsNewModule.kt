package com.egoriku.grodnoroads.settings.whatsnew.di

import com.egoriku.grodnoroads.settings.whatsnew.data.repository.WhatsNewRepositoryImpl
import com.egoriku.grodnoroads.settings.whatsnew.domain.component.WhatsNewComponent
import com.egoriku.grodnoroads.settings.whatsnew.domain.component.WhatsNewComponentImpl
import com.egoriku.grodnoroads.settings.whatsnew.domain.repository.WhatsNewRepository
import com.egoriku.grodnoroads.settings.whatsnew.domain.store.WhatsNewStoreFactory
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val whatsNewModule = module {
    factoryOf(::WhatsNewRepositoryImpl) { bind<WhatsNewRepository>() }

    factoryOf(::WhatsNewComponentImpl) { bind<WhatsNewComponent>() }

    factory {
        WhatsNewStoreFactory(
            storeFactory = get(),
            whatsNewRepository = get(),
            crashlyticsTracker = get()
        ).create()
    }
}