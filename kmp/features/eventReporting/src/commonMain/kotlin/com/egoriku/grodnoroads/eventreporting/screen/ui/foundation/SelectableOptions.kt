package com.egoriku.grodnoroads.eventreporting.screen.ui.foundation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.eventreporting.domain.Reporting
import com.egoriku.grodnoroads.eventreporting.screen.ui.util.toStringResource
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoads
import com.egoriku.grodnoroads.foundation.uikit.VerticalSpacer
import com.egoriku.grodnoroads.foundation.uikit.listitem.RadioButtonListItem
import com.egoriku.grodnoroads.shared.models.reporting.ReportParams
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ColumnScope.SelectableOptions(
    reportType: Reporting.ReportType,
    onReportParamsChange: (ReportParams) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val updatedReportParamsChange by rememberUpdatedState(onReportParamsChange)

    var selectedOption by rememberMutableState(reportType) {
        reportType.items.first()
    }

    LaunchedEffect(reportType, selectedOption) {
        updatedReportParamsChange(
            ReportParams.EventReport(
                mapEventType = selectedOption.mapEventType,
                shortMessage = selectedOption.toSend,
                message = selectedOption.toSend
            )
        )
    }

    reportType.items.forEach { entry ->
        RadioButtonListItem(
            text = stringResource(entry.toStringResource()),
            selected = selectedOption == entry,
            onClick = {
                selectedOption = entry
                focusManager.clearFocus()
            }
        )
    }
    VerticalSpacer(8.dp)
}

@PreviewGrodnoRoads
@Composable
private fun SelectableOptionsPreview() = GrodnoRoadsM3ThemePreview {
    Column {
        SelectableOptions(
            reportType = Reporting.ReportType.RoadIncidents,
            onReportParamsChange = {}
        )
    }
}
