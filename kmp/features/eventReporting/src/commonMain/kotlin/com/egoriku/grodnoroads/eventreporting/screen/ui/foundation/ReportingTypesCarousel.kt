package com.egoriku.grodnoroads.eventreporting.screen.ui.foundation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.reporting_category_mobile_camera
import com.egoriku.grodnoroads.compose.resources.reporting_category_other
import com.egoriku.grodnoroads.compose.resources.reporting_category_road_incidents
import com.egoriku.grodnoroads.compose.resources.reporting_category_traffic_police
import com.egoriku.grodnoroads.eventreporting.domain.Reporting.ReportType
import com.egoriku.grodnoroads.foundation.core.AutoScrollLazyRow
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.colored.MobileCameraBold
import com.egoriku.grodnoroads.foundation.icons.colored.RoadIncidentBold
import com.egoriku.grodnoroads.foundation.icons.colored.RoadProblemBold
import com.egoriku.grodnoroads.foundation.icons.colored.TrafficPoliceBold
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ReportingTypesCarousel(
    currentType: ReportType,
    onTypeChange: (ReportType) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val items = remember {
        listOf(
            Repo(
                reportType = ReportType.RoadIncidents,
                imageVector = GrodnoRoads.Colored.RoadProblemBold,
                stringResource = Res.string.reporting_category_road_incidents
            ),
            Repo(
                reportType = ReportType.TrafficPolice,
                imageVector = GrodnoRoads.Colored.TrafficPoliceBold,
                stringResource = Res.string.reporting_category_traffic_police
            ),
            Repo(
                reportType = ReportType.Other,
                imageVector = GrodnoRoads.Colored.RoadIncidentBold,
                stringResource = Res.string.reporting_category_other
            ),
            Repo(
                reportType = ReportType.MobileCamera,
                imageVector = GrodnoRoads.Colored.MobileCameraBold,
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
                imageVector = it.imageVector,
                selected = currentType == it.reportType,
                onClick = {
                    onTypeChange(it.reportType)
                    focusManager.clearFocus()
                }
            )
        }
    }
}

@Composable
private fun CategoryCell(
    name: String,
    imageVector: ImageVector,
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
            imageVector = imageVector,
            contentDescription = null
        )
        Text(
            text = name,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center
        )
    }
}

private data class Repo(
    val reportType: ReportType,
    val imageVector: ImageVector,
    val stringResource: StringResource
)
