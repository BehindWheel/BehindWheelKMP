package com.egoriku.grodnoroads.screen.settings.faq.di

import com.egoriku.grodnoroads.screen.settings.faq.data.FaqRepository
import com.egoriku.grodnoroads.screen.settings.faq.data.FaqRepositoryImpl
import com.egoriku.grodnoroads.screen.settings.faq.domain.store.FaqStoreFactory
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val faqModule = module {
    factoryOf(::FaqRepositoryImpl) { bind<FaqRepository>() }

    factory {
        FaqStoreFactory(
            storeFactory = get(),
            faqRepository = get()
        ).create()
    }
}