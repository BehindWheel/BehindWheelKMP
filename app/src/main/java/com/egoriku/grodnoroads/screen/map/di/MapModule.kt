package com.egoriku.grodnoroads.screen.map.di

import com.egoriku.grodnoroads.screen.map.domain.component.MapComponent
import com.egoriku.grodnoroads.screen.map.domain.component.MapComponentImpl
import com.egoriku.grodnoroads.screen.map.domain.store.DialogStoreFactory
import com.egoriku.grodnoroads.screen.map.domain.store.LocationStoreFactory
import com.egoriku.grodnoroads.screen.map.domain.store.MapConfigStoreFactory
import com.egoriku.grodnoroads.screen.map.domain.store.MapEventsStoreFactory
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val mapModule = module {

    factoryOf(::MapComponentImpl) { bind<MapComponent>() }

    factory {
        MapConfigStoreFactory(
            storeFactory = get(),
            dataStore = get()
        ).create()
    }
    factory {
        MapEventsStoreFactory(
            storeFactory = get(),
            mobileCameraRepository = get(),
            stationaryCameraRepository = get(),
            reportsRepository = get(),
            firebaseAnalytics = get()
        ).create()
    }

    factory {
        LocationStoreFactory(
            storeFactory = get(),
            locationHelper = get(),
            resourceProvider = get(),
            datastore = get()
        ).create()
    }

    factory {
        DialogStoreFactory(
            storeFactory = get(),
            firebaseAnalytics = get()
        ).create()
    }
}

