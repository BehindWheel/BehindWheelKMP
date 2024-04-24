package com.egoriku.grodnoroads.guidance.di

import com.egoriku.grodnoroads.guidance.data.repository.MediumSpeedCameraRepositoryImpl
import com.egoriku.grodnoroads.guidance.data.repository.MobileCameraRepositoryImpl
import com.egoriku.grodnoroads.guidance.data.repository.ReportsRepositoryImpl
import com.egoriku.grodnoroads.guidance.data.repository.StationaryCameraRepositoryImpl
import com.egoriku.grodnoroads.guidance.data.repository.UserCountRepositoryImpl
import com.egoriku.grodnoroads.guidance.domain.repository.MediumSpeedCameraRepository
import com.egoriku.grodnoroads.guidance.domain.repository.MobileCameraRepository
import com.egoriku.grodnoroads.guidance.domain.repository.ReportsRepository
import com.egoriku.grodnoroads.guidance.domain.repository.StationaryCameraRepository
import com.egoriku.grodnoroads.guidance.domain.repository.UserCountRepository
import com.egoriku.grodnoroads.guidance.domain.store.config.MapConfigStoreFactory
import com.egoriku.grodnoroads.guidance.domain.store.dialog.DialogStoreFactory
import com.egoriku.grodnoroads.guidance.domain.store.location.LocationStoreFactory
import com.egoriku.grodnoroads.guidance.domain.store.mapevents.MapEventsStoreFactory
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val guidanceModule = module {
    factoryOf(::UserCountRepositoryImpl) { bind<UserCountRepository>() }
    factoryOf(::StationaryCameraRepositoryImpl) { bind<StationaryCameraRepository>() }
    factoryOf(::MediumSpeedCameraRepositoryImpl) { bind<MediumSpeedCameraRepository>() }
    factoryOf(::MobileCameraRepositoryImpl) { bind<MobileCameraRepository>() }
    factoryOf(::ReportsRepositoryImpl) { bind<ReportsRepository>() }

    factory {
        MapEventsStoreFactory(
            storeFactory = get(),
            mobileCameraRepository = get(),
            stationaryCameraRepository = get(),
            mediumSpeedCameraRepository = get(),
            userCountRepository = get(),
            reportsRepository = get(),
            // analyticsTracker = get(),
            //  crashlyticsTracker = get()
            dataStore = get()
        ).create()
    }

    factory {
        LocationStoreFactory(
            storeFactory = get(),
            locationService = get(),
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
            // analyticsTracker = get()
        ).create()
    }
}