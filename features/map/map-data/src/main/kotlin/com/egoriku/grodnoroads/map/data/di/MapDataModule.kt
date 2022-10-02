package com.egoriku.grodnoroads.map.data.di

import com.egoriku.grodnoroads.map.data.repository.MobileCameraRepositoryImpl
import com.egoriku.grodnoroads.map.data.repository.ReportsRepositoryImpl
import com.egoriku.grodnoroads.map.data.repository.StationaryCameraRepositoryImpl
import com.egoriku.grodnoroads.map.domain.repository.MobileCameraRepository
import com.egoriku.grodnoroads.map.domain.repository.ReportsRepository
import com.egoriku.grodnoroads.map.domain.repository.StationaryCameraRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val mapDataModule = module {
    factoryOf(::MobileCameraRepositoryImpl) { bind<MobileCameraRepository>() }
    factoryOf(::ReportsRepositoryImpl) { bind<ReportsRepository>() }
    factoryOf(::StationaryCameraRepositoryImpl) { bind<StationaryCameraRepository>() }
}