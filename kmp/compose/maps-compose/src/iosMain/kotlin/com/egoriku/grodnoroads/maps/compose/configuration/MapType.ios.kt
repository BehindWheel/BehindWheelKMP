package com.egoriku.grodnoroads.maps.compose.configuration

import cocoapods.GoogleMaps.GMSMapViewType
import cocoapods.GoogleMaps.kGMSTypeHybrid
import cocoapods.GoogleMaps.kGMSTypeNormal
import cocoapods.GoogleMaps.kGMSTypeSatellite
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
fun MapType.toiOSMapType(): GMSMapViewType = when (this) {
    MapType.Normal -> kGMSTypeNormal
    MapType.Satellite -> kGMSTypeSatellite
    MapType.Hybrid -> kGMSTypeHybrid
}
