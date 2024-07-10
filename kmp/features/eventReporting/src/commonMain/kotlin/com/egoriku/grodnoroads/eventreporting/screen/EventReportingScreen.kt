package com.egoriku.grodnoroads.eventreporting.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.nt_ic_mobile_camera_bold
import com.egoriku.grodnoroads.compose.resources.nt_ic_road_incident_bold
import com.egoriku.grodnoroads.compose.resources.nt_ic_road_problem_bold
import com.egoriku.grodnoroads.compose.resources.nt_ic_traffic_police_bold
import com.egoriku.grodnoroads.compose.resources.reporting_category_mobile_camera
import com.egoriku.grodnoroads.compose.resources.reporting_category_other
import com.egoriku.grodnoroads.compose.resources.reporting_category_road_incidents
import com.egoriku.grodnoroads.compose.resources.reporting_category_traffic_police
import com.egoriku.grodnoroads.compose.resources.reporting_header
import com.egoriku.grodnoroads.eventreporting.domain.Reporting.ReportType
import com.egoriku.grodnoroads.eventreporting.screen.ui.ActionBottomSheet
import com.egoriku.grodnoroads.eventreporting.screen.ui.foundation.MobileCameraOptions
import com.egoriku.grodnoroads.eventreporting.screen.ui.foundation.SelectableOptions
import com.egoriku.grodnoroads.foundation.core.AutoScrollLazyRow
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.VerticalSpacer
import com.egoriku.grodnoroads.shared.models.reporting.ReportParams
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun EventReportingScreen(
    onClose: () -> Unit,
    onReport: (ReportParams) -> Unit
) {
    var reportParams by rememberMutableState<ReportParams?> { null }
    val sendEnabled by remember {
        derivedStateOf {
            when (val params = reportParams) {
                is ReportParams.EventReport -> true
                is ReportParams.MobileCameraReport -> params.cameraInfo.isNotEmpty()
                null -> false
            }
        }
    }

    ActionBottomSheet(
        onDismiss = onClose,
        sendEnabled = sendEnabled,
        onResult = { reportParams?.let(onReport) }
    ) {
        ReportingUi(onReportParamsChange = { reportParams = it })
    }
}

@Composable
private fun ReportingUi(onReportParamsChange: (ReportParams) -> Unit) {
    val focusManager = LocalFocusManager.current

    var reportType by rememberMutableState<ReportType> { ReportType.RoadIncidents }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .verticalScroll(rememberScrollState())
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            text = stringResource(Res.string.reporting_header),
            style = MaterialTheme.typography.headlineSmall
        )
        VerticalSpacer(16.dp)
        ReportingTypes(currentType = reportType, onTypeChanged = { reportType = it })
        VerticalSpacer(16.dp)

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
        VerticalSpacer(8.dp)
    }
}

internal data class Repo(
    val reportType: ReportType,
    val drawableResource: DrawableResource,
    val stringResource: StringResource
)

@Composable
private fun ReportingTypes(
    currentType: ReportType,
    onTypeChanged: (ReportType) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val items = remember {
        persistentListOf(
            Repo(
                reportType = ReportType.RoadIncidents,
                drawableResource = Res.drawable.nt_ic_road_problem_bold,
                stringResource = Res.string.reporting_category_road_incidents
            ),
            Repo(
                reportType = ReportType.TrafficPolice,
                drawableResource = Res.drawable.nt_ic_traffic_police_bold,
                stringResource = Res.string.reporting_category_traffic_police
            ),
            Repo(
                reportType = ReportType.Other,
                drawableResource = Res.drawable.nt_ic_road_incident_bold,
                stringResource = Res.string.reporting_category_other
            ),
            Repo(
                reportType = ReportType.MobileCamera,
                drawableResource = Res.drawable.nt_ic_mobile_camera_bold,
                stringResource = Res.string.reporting_category_mobile_camera
            )
        )
    }

    val indexToScroll by remember(currentType) {
        derivedStateOf { items.indexOfFirst { it.reportType == currentType } }
    }
    AutoScrollLazyRow(
        indexToScroll = indexToScroll,
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items) {
            CategoryCell(
                modifier = Modifier.width(96.dp),
                name = stringResource(it.stringResource),
                drawableResource = it.drawableResource,
                selected = currentType == it.reportType,
                onClick = {
                    onTypeChanged(it.reportType)
                    focusManager.clearFocus()
                }
            )
        }
    }
}

@Composable
private fun CategoryCell(
    name: String,
    drawableResource: DrawableResource,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val selectedModifier = if (selected) {
        Modifier.border(
            width = 2.dp,
            color = MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(10.dp)
        )
    } else {
        Modifier
    }
    Column(
        modifier = modifier
            .heightIn(min = 132.dp)
            .then(selectedModifier)
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            modifier = Modifier.size(64.dp),
            painter = painterResource(drawableResource),
            contentDescription = null
        )
        Text(
            text = name,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center
        )
    }
}

@GrodnoRoadsPreview
@Composable
private fun ReportingUiPreview() = GrodnoRoadsM3ThemePreview {
    ReportingUi(onReportParamsChange = {})
}