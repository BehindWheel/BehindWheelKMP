package com.egoriku.grodnoroads.map.domain.di

import com.egoriku.grodnoroads.map.domain.store.config.MapConfigStoreFactory
import com.egoriku.grodnoroads.map.domain.store.dialog.DialogStoreFactory
import com.egoriku.grodnoroads.map.domain.store.location.LocationStoreFactory
import com.egoriku.grodnoroads.map.domain.store.mapevents.MapEventsStoreFactory
import com.egoriku.grodnoroads.map.domain.store.quickactions.QuickActionsStoreFactory
import org.koin.dsl.module

val mapDomainModule = module {
    factory {
        MapEventsStoreFactory(
            storeFactory = get(),
            mobileCameraRepository = get(),
            stationaryCameraRepository = get(),
            mediumSpeedCameraRepository = get(),
            userCountRepository = get(),
            reportsRepository = get(),
            crashlyticsTracker = get(),
            dataStore = get()
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

