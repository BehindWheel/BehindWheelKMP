package com.egoriku.grodnoroads.eventreporting.screen.ui.foundation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.reporting_mobile_camera_header
import com.egoriku.grodnoroads.compose.resources.reporting_mobile_camera_speed
import com.egoriku.grodnoroads.foundation.core.AutoScrollLazyRow
import com.egoriku.grodnoroads.foundation.core.CenterVerticallyRow
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoads
import com.egoriku.grodnoroads.foundation.uikit.FilterChip
import com.egoriku.grodnoroads.shared.models.reporting.ReportParams
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun MobileCameraOptions(onReportParamsChange: (ReportParams) -> Unit) {
    val updatedReportParamsChange by rememberUpdatedState(onReportParamsChange)

    val focusManager = LocalFocusManager.current

    val speedLimits = remember { persistentListOf(40, 50, 60, 70, 80, 90, 100, 110, 120) }
    var selectedSpeedLimit by rememberMutableState { speedLimits[2] }

    LaunchedEffect(selectedSpeedLimit) {
        updatedReportParamsChange(
            ReportParams.MobileCameraReport(
                speedLimit = selectedSpeedLimit,
                cameraInfo = ""
            )
        )
    }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        CenterVerticallyRow(
            modifier = Modifier.padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(Res.string.reporting_mobile_camera_header),
                style = MaterialTheme.typography.titleMedium
            )
        }

        val indexToScroll by remember {
            derivedStateOf { speedLimits.indexOfFirst { it == selectedSpeedLimit } }
        }
        AutoScrollLazyRow(indexToScroll = indexToScroll) {
            items(speedLimits) { limit ->
                val selected = selectedSpeedLimit == limit

                FilterChip(
                    label = {
                        Text(
                            text = stringResource(Res.string.reporting_mobile_camera_speed, limit),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    selected = selected,
                    onClick = {
                        selectedSpeedLimit = limit
                        focusManager.clearFocus()
                    }
                )
            }
        }
    }
}

@PreviewGrodnoRoads
@Composable
private fun MobileCameraOptionsPreview() = GrodnoRoadsM3ThemePreview {
    MobileCameraOptions(onReportParamsChange = {})
}
