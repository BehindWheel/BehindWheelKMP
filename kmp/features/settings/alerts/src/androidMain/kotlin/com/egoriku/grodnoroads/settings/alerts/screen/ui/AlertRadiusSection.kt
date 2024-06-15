package com.egoriku.grodnoroads.settings.alerts.screen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.alerts_header_notification_radius
import com.egoriku.grodnoroads.compose.resources.alerts_notification_radius_in_city
import com.egoriku.grodnoroads.compose.resources.alerts_notification_radius_outside_city
import com.egoriku.grodnoroads.compose.resources.alerts_notification_radius_postfix
import com.egoriku.grodnoroads.compose.resources.ic_inside_city
import com.egoriku.grodnoroads.compose.resources.ic_outside_city
import com.egoriku.grodnoroads.foundation.common.ui.SettingsSectionHeader
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.ClickableIntRange
import com.egoriku.grodnoroads.foundation.uikit.listitem.BasicListItem
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertSettings.AlertRadius
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertsPref
import org.jetbrains.compose.resources.stringResource

@Composable
fun AlertRadiusSection(
    alertRadius: AlertRadius,
    modify: (AlertsPref) -> Unit,
    reset: (AlertsPref) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        SettingsSectionHeader(title = stringResource(Res.string.alerts_header_notification_radius))

        val postfix = stringResource(Res.string.alerts_notification_radius_postfix)
        val radiusInCity = alertRadius.alertRadiusInCity

        BasicListItem(
            drawableResource = Res.drawable.ic_inside_city,
            text = stringResource(Res.string.alerts_notification_radius_in_city),
            textStyle = MaterialTheme.typography.bodyMedium
        ) {
            ClickableIntRange(
                value = radiusInCity.current,
                min = radiusInCity.min,
                max = radiusInCity.max,
                step = radiusInCity.stepSize,
                formatter = { "$it $postfix" },
                onLongClick = { reset(alertRadius.alertRadiusInCity) },
                onValueChange = {
                    modify(radiusInCity.copy(current = it))
                },
            )
        }

        val radiusOutCity = alertRadius.alertRadiusOutCity
        BasicListItem(
            drawableResource = Res.drawable.ic_outside_city,
            text = stringResource(Res.string.alerts_notification_radius_outside_city),
            textStyle = MaterialTheme.typography.bodyMedium
        ) {
            ClickableIntRange(
                value = radiusOutCity.current,
                min = radiusOutCity.min,
                max = radiusOutCity.max,
                step = radiusOutCity.stepSize,
                formatter = { "$it $postfix" },
                onLongClick = { reset(radiusOutCity) },
                onValueChange = {
                    modify(radiusOutCity.copy(current = it))
                },
            )
        }
    }
}

@GrodnoRoadsPreview
@Composable
private fun AlertRadiusSectionPreview() = GrodnoRoadsM3ThemePreview {
    AlertRadiusSection(
        alertRadius = AlertRadius(),
        modify = {},
        reset = {}
    )
}