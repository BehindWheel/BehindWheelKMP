package com.egoriku.grodnoroads.screen.settings.map.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.foundation.list.CheckboxSettings
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.GoogleMapStyle.Style.Detailed
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.GoogleMapStyle.Style.Minimal
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapSettingsState.MapStyle
import com.egoriku.grodnoroads.screen.settings.ui.SettingsHeader
import com.egoriku.grodnoroads.ui.theme.GrodnoRoadsTheme

@Composable
fun MapStyleSection(
    mapStyle: MapStyle,
    onCheckedChange: (MapPref) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        SettingsHeader(title = stringResource(id = R.string.map_header_appearance))

        GoogleMapStyle(mapStyle, onCheckedChange)
        TrafficJam(mapStyle, onCheckedChange)
    }
}

@Composable
private fun GoogleMapStyle(
    mapStyle: MapStyle,
    onCheckedChange: (MapPref) -> Unit
) {
    val googleMapStyle = mapStyle.googleMapStyle

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        CheckableCard(
            title = R.string.map_google_map_style_minimal,
            selected = googleMapStyle.style == Minimal,
            iconId = R.drawable.ic_map_style_minimal,
            onClick = {
                onCheckedChange(googleMapStyle.copy(style = Minimal))
            }
        )
        CheckableCard(
            title = R.string.map_google_map_style_detailed,
            selected = googleMapStyle.style == Detailed,
            iconId = R.drawable.ic_map_style_detailed,
            onClick = {
                onCheckedChange(googleMapStyle.copy(style = Detailed))
            }
        )
    }
}

@Composable
private fun TrafficJam(
    mapStyle: MapStyle,
    onCheckedChange: (MapPref) -> Unit
) {
    val trafficJam = mapStyle.trafficJam

    CheckboxSettings(
        iconRes = R.drawable.ic_traffic_light,
        textRes = R.string.map_traffic_jam_appearance,
        isChecked = trafficJam.isShow,
        onCheckedChange = {
            onCheckedChange(trafficJam.copy(isShow = it))
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CheckableCard(
    title: Int,
    selected: Boolean,
    iconId: Int,
    onClick: () -> Unit
) {
    Column(modifier = Modifier.width(120.dp)) {
        Card(
            modifier = Modifier.size(120.dp),
            onClick = onClick,
            shape = RoundedCornerShape(20.dp),
            border = when {
                selected -> BorderStroke(width = 1.5.dp, color = MaterialTheme.colors.secondary)
                else -> null
            }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = iconId),
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
            }
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            text = stringResource(id = title),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body2,
            fontWeight = when {
                selected -> FontWeight.Bold
                else -> null
            }
        )
    }
}

@Preview
@Composable
private fun CheckableCardPreview() {
    GrodnoRoadsTheme {
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            CheckableCard(
                title = R.string.map_google_map_style_minimal,
                selected = false,
                iconId = R.drawable.ic_map_style_minimal
            ) {}
            CheckableCard(
                title = R.string.map_google_map_style_detailed,
                selected = true,
                iconId = R.drawable.ic_map_style_detailed
            ) {}
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, locale = "ru")
@Composable
fun PreviewMapStyleSection() {
    GrodnoRoadsTheme {
        MapStyleSection(mapStyle = MapStyle()) { }
    }
}
