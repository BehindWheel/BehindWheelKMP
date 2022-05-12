package com.egoriku.grodnoroads.koin

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.egoriku.grodnoroads.util.MarkerCache
import com.egoriku.grodnoroads.util.ResourceProvider
import com.egoriku.grodnoroads.util.ResourceProviderImpl
import com.egoriku.grodnoroads.util.location.LocationHelper
import com.egoriku.grodnoroads.util.location.LocationHelperImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appScopeModule = module {
    singleOf(::DefaultStoreFactory) { bind<StoreFactory>()}
    singleOf(::LocationHelperImpl) { bind<LocationHelper>()}
    singleOf(::ResourceProviderImpl) { bind<ResourceProvider>()}

    singleOf(::MarkerCache)
}