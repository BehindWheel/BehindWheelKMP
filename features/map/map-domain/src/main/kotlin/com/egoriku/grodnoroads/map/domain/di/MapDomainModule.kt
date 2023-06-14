package com.egoriku.grodnoroads.map.domain.di

import com.egoriku.grodnoroads.map.domain.component.MapComponent
import com.egoriku.grodnoroads.map.domain.component.MapComponentImpl
import com.egoriku.grodnoroads.map.domain.store.config.MapConfigStoreFactory
import com.egoriku.grodnoroads.map.domain.store.dialog.DialogStoreFactory
import com.egoriku.grodnoroads.map.domain.store.location.LocationStoreFactory
import com.egoriku.grodnoroads.map.domain.store.mapevents.MapEventsStoreFactory
import com.egoriku.grodnoroads.map.domain.store.quickactions.QuickActionsStoreFactory
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val mapDomainModule = module {

    factoryOf(::MapComponentImpl) { bind<MapComponent>() }

    factory {
        MapEventsStoreFactory(
            storeFactory = get(),
            mobileCameraRepository = get(),
            stationaryCameraRepository = get(),
            mediumSpeedCameraRepository = get(),
            userCountRepository = get(),
            reportsRepository = get(),
            analyticsTracker = get(),
            crashlyticsTracker = get()
        ).create()
    }

    factory {
        LocationStoreFactory(
            storeFactory = get(),
            locationHelper = get(),
            dataStore = get()
        ).create()
    }

    factory {
        MapConfigStoreFactory(
            storeFactory = get(),
            dataStore = get()
        ).create()
    }

    factory {
        DialogStoreFactory(
            storeFactory = get(),
            analyticsTracker = get()
        ).create()
    }

    factory {
        QuickActionsStoreFactory(
            storeFactory = get(),
            dataStore = get()
        ).create()
    }
}

