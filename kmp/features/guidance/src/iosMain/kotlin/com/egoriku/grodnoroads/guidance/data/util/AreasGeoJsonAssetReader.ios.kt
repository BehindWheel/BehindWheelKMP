package com.egoriku.grodnoroads.guidance.data.util

import com.egoriku.grodnoroads.multiplatformresources.MR

actual object AreasGeoJsonAssetReader {

    actual fun readAreasFile() = MR.files.areas_geojson.readText()
}