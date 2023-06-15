package com.egoriku.grodnoroads.setting.faq.di

import com.egoriku.grodnoroads.setting.faq.data.repository.FaqRepositoryImpl
import com.egoriku.grodnoroads.setting.faq.domain.repository.FaqRepository
import com.egoriku.grodnoroads.setting.faq.domain.store.FaqStoreFactory
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val faqModule = module {
    factoryOf(::FaqRepositoryImpl) { bind<FaqRepository>() }

    factory {
        FaqStoreFactory(
            storeFactory = get(),
            faqRepository = get(),
            crashlyticsTracker = get()
        ).create()
    }
}