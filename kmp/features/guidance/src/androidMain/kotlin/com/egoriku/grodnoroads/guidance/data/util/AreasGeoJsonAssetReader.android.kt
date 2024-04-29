package com.egoriku.grodnoroads.guidance.data.util

import android.content.Context
import com.egoriku.grodnoroads.multiplatformresources.MR
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

actual object AreasGeoJsonAssetReader : KoinComponent {

    actual fun readAreasFile(): String {
        return MR.files.areas_geojson.readText(context = get<Context>())
    }
}