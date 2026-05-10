package com.egoriku.grodnoroads.maps.compose.configuration

import kotlinx.cinterop.ExperimentalForeignApi
import swiftPMImport.Grodno.Roads.kmp.compose.kmp.compose.maps.compose.GMSMapViewType
import swiftPMImport.Grodno.Roads.kmp.compose.kmp.compose.maps.compose.kGMSTypeHybrid
import swiftPMImport.Grodno.Roads.kmp.compose.kmp.compose.maps.compose.kGMSTypeNormal
import swiftPMImport.Grodno.Roads.kmp.compose.kmp.compose.maps.compose.kGMSTypeSatellite

@OptIn(ExperimentalForeignApi::class)
fun MapType.toiOSMapType(): GMSMapViewType = when (this) {
    MapType.Normal -> kGMSTypeNormal
    MapType.Satellite -> kGMSTypeSatellite
    MapType.Hybrid -> kGMSTypeHybrid
}
