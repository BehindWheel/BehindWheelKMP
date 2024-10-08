package com.egoriku.grodnoroads.specialevent.di

import com.egoriku.grodnoroads.specialevent.domain.store.SpecialEventStoreFactory
import org.koin.dsl.module

val specialEventModule = module {
    factory {
        SpecialEventStoreFactory(storeFactory = get()).create()
    }
}
