package com.egoriku.grodnoroads.map.domain.model

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.map.domain.model.MapEvent.Reports

@Stable
sealed interface MapBottomSheet {

    data class MarkerInfo(val reports: Reports) : MapBottomSheet
    data object QuickSettings : MapBottomSheet
    data object None : MapBottomSheet
}