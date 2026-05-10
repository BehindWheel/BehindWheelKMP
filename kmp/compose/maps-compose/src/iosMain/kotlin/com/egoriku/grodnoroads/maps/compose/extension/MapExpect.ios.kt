package com.egoriku.grodnoroads.maps.compose.extension

import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIImage
import swiftPMImport.Grodno.Roads.kmp.compose.kmp.compose.maps.compose.GMSMapView

@OptIn(ExperimentalForeignApi::class)
actual typealias GoogleMap = GMSMapView

actual typealias MarkerImage = UIImage
