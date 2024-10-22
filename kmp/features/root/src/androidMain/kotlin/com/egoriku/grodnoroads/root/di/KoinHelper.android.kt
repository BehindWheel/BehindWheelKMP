package com.egoriku.grodnoroads.root.di

import android.content.Context
import com.egoriku.grodnoroads.datastore.dataStore
import com.egoriku.grodnoroads.guidance.di.guidanceModule
import com.egoriku.grodnoroads.shared.geolocation.AndroidLocationService
import com.egoriku.grodnoroads.shared.geolocation.LocationService
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal actual val platformDataStoreModule = module {
    includes(guidanceModule)
    single { get<Context>().dataStore() }
    singleOf(::AndroidLocationService) { bind<LocationService>() }
}
