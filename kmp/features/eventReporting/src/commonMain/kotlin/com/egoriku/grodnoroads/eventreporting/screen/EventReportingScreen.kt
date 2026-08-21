package com.egoriku.grodnoroads.eventreporting.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.reporting_header
import com.egoriku.grodnoroads.eventreporting.domain.Reporting.ReportType
import com.egoriku.grodnoroads.eventreporting.screen.ui.foundation.BottomActions
import com.egoriku.grodnoroads.eventreporting.screen.ui.foundation.MobileCameraOptions
import com.egoriku.grodnoroads.eventreporting.screen.ui.foundation.ReportingOptionalMessage
import com.egoriku.grodnoroads.eventreporting.screen.ui.foundation.ReportingTypesCarousel
import com.egoriku.grodnoroads.eventreporting.screen.ui.foundation.SelectableOptions
import com.egoriku.grodnoroads.foundation.common.ui.bottomsheet.BasicModalBottomSheet
import com.egoriku.grodnoroads.foundation.common.ui.bottomsheet.rememberSheetCloseBehaviour
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.layout.Spacer
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoads
import com.egoriku.grodnoroads.shared.models.reporting.ReportParams
import org.jetbrains.compose.resources.stringResource

internal const val MAX_CAMERA_DESCRIPTION_SYMBOLS = 50
internal const val MAX_EVENT_DESCRIPTION_SYMBOLS = 120

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventReportingScreen(
    onClose: () -> Unit,
    onReport: (ReportParams) -> Unit
) {
    var reportType by rememberMutableState<ReportType> { ReportType.RoadIncidents }
    var reportParams by rememberMutableState<ReportParams?> { null }
    val sendEnabled by remember {
        derivedStateOf {
            when (val params = reportParams) {
                is ReportParams.EventReport -> params.message.length <= MAX_EVENT_DESCRIPTION_SYMBOLS
                is ReportParams.MobileCameraReport -> params.cameraInfo.length <= MAX_CAMERA_DESCRIPTION_SYMBOLS
                null -> false
            }
        }
    }
    val sheetCloseBehaviour = rememberSheetCloseBehaviour(
        onCancel = onClose,
        onResult = {
            reportParams?.let(onReport)
        }
    )

    BasicModalBottomSheet(
        sheetState = sheetCloseBehaviour.sheetState,
        onCancel = onClose,
        content = {
            ReportingUi(
                reportType = reportType,
                onReportTypeChange = { reportType = it },
                onReportParamsChange = { reportParams = it }
            )
        },
        footer = {
            ReportingOptionalMessage(
                reportType = reportType,
                onUpdateMessage = {
                    when (val params = reportParams) {
                        is ReportParams.EventReport -> {
                            reportParams = params.copy(message = it.ifEmpty { params.shortMessage })
                        }
                        is ReportParams.MobileCameraReport -> {
                            reportParams = params.copy(cameraInfo = it)
                        }
                        null -> {}
                    }
                }
            )
            BottomActions(
                sendEnabled = sendEnabled,
                onCancel = sheetCloseBehaviour::cancel,
                onResult = sheetCloseBehaviour::hideWithResult
            )
        }
    )
}

@Composable
private fun ReportingUi(
    reportType: ReportType,
    onReportTypeChange: (ReportType) -> Unit,
    onReportParamsChange: (ReportParams) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .verticalScroll(rememberScrollState())
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            text = stringResource(Res.string.reporting_header),
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(16.dp)
        ReportingTypesCarousel(
            currentType = reportType,
            onTypeChange = onReportTypeChange
        )
        Spacer(16.dp)

        when (reportType) {
            ReportType.MobileCamera -> {
                MobileCameraOptions(onReportParamsChange = onReportParamsChange)
            }
            else -> {
                SelectableOptions(
                    reportType = reportType,
                    onReportParamsChange = onReportParamsChange
                )
            }
        }
    }
}

@PreviewGrodnoRoads
@Composable
private fun ReportingUiPreview() = GrodnoRoadsM3ThemePreview {
    ReportingUi(
        reportType = ReportType.TrafficPolice,
        onReportTypeChange = {},
        onReportParamsChange = {}
    )
}
