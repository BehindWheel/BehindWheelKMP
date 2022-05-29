package com.egoriku.grodnoroads.screen.map.koin

import com.egoriku.grodnoroads.screen.map.MapComponent
import com.egoriku.grodnoroads.screen.map.MapComponentImpl
import com.egoriku.grodnoroads.screen.map.data.*
import com.egoriku.grodnoroads.screen.map.store.MapEventsStoreFactory
import com.egoriku.grodnoroads.screen.map.store.LocationStoreFactory
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val mapModule = module {
    factoryOf(::MobileCameraRepositoryImpl) { bind<MobileCameraRepository>() }
    factoryOf(::ReportsRepositoryImpl) { bind<ReportsRepository>() }
    factoryOf(::StationaryCameraRepositoryImpl) { bind<StationaryCameraRepository>() }

    factoryOf(::MapComponentImpl) { bind<MapComponent>() }

    factory {
        MapEventsStoreFactory(
            storeFactory = get(),
            mobileCameraRepository = get(),
            stationaryCameraRepository = get(),
            reportsRepository = get()
        ).create()
    }

    factory {
        LocationStoreFactory(
            storeFactory = get(),
            locationHelper = get(),
            resourceProvider = get()
        ).create()
    }
}

