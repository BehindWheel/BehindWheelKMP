package com.egoriku.grodnoroads.koin

import android.content.Context
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.egoriku.grodnoroads.screen.settings.store.preferences.dataStore
import com.egoriku.grodnoroads.util.MarkerCache
import com.egoriku.grodnoroads.util.ResourceProvider
import com.egoriku.grodnoroads.util.ResourceProviderImpl
import com.egoriku.grodnoroads.util.location.LocationHelper
import com.egoriku.grodnoroads.util.location.LocationHelperImpl
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appScopeModule = module {
    single { Firebase.database.reference }
    single { get<Context>().dataStore }

    singleOf(::DefaultStoreFactory) { bind<StoreFactory>() }
    singleOf(::LocationHelperImpl) { bind<LocationHelper>() }
    singleOf(::ResourceProviderImpl) { bind<ResourceProvider>() }

    singleOf(::MarkerCache)
}