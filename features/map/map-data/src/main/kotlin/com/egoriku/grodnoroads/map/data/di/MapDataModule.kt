package com.egoriku.grodnoroads.map.data.di

import com.egoriku.grodnoroads.map.data.repository.*
import com.egoriku.grodnoroads.map.domain.repository.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val mapDataModule = module {
    factoryOf(::StationaryCameraRepositoryImpl) { bind<StationaryCameraRepository>() }
    factoryOf(::MediumSpeedCameraRepositoryImpl) { bind<MediumSpeedCameraRepository>() }
    factoryOf(::MobileCameraRepositoryImpl) { bind<MobileCameraRepository>() }
    factoryOf(::ReportsRepositoryImpl) { bind<ReportsRepository>() }
    factoryOf(::UserCountRepositoryImpl) { bind<UserCountRepository>() }
}