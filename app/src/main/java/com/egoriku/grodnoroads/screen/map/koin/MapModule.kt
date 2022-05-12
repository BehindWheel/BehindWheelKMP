package com.egoriku.grodnoroads.screen.map.koin

import com.egoriku.grodnoroads.data.repository.ReportActionRepositoryImpl
import com.egoriku.grodnoroads.domain.repository.ReportActionRepository
import com.egoriku.grodnoroads.domain.usecase.CameraUseCase
import com.egoriku.grodnoroads.domain.usecase.CameraUseCaseImpl
import com.egoriku.grodnoroads.screen.map.MapComponent
import com.egoriku.grodnoroads.screen.map.MapComponentImpl
import com.egoriku.grodnoroads.screen.map.data.MobileCameraRepository
import com.egoriku.grodnoroads.screen.map.data.MobileCameraRepositoryImpl
import com.egoriku.grodnoroads.screen.map.data.StationaryCameraRepository
import com.egoriku.grodnoroads.screen.map.data.StationaryCameraRepositoryImpl
import com.egoriku.grodnoroads.screen.map.store.CamerasStoreFactory
import com.egoriku.grodnoroads.screen.map.store.LocationStoreFactory
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val mapModule = module {
    factoryOf(::MobileCameraRepositoryImpl) { bind<MobileCameraRepository>() }
    factoryOf(::StationaryCameraRepositoryImpl) { bind<StationaryCameraRepository>() }
    factoryOf(::ReportActionRepositoryImpl) { bind<ReportActionRepository>() }

    factoryOf(::CameraUseCaseImpl) { bind<CameraUseCase>() }

    factoryOf(::MapComponentImpl) { bind<MapComponent>() }

    factory {
        CamerasStoreFactory(
            storeFactory = get(),
            cameraUseCase = get(),
            mobileCameraRepository = get(),
            stationaryCameraRepository = get()
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

