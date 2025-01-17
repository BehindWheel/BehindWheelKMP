package com.egoriku.grodnoroads.foundation.icons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.egoriku.grodnoroads.foundation.icons.colored.App
import com.egoriku.grodnoroads.foundation.icons.colored.AppDark
import com.egoriku.grodnoroads.foundation.icons.colored.CarCrash
import com.egoriku.grodnoroads.foundation.icons.colored.Info
import com.egoriku.grodnoroads.foundation.icons.colored.MediumSpeedCamera
import com.egoriku.grodnoroads.foundation.icons.colored.MediumSpeedCameraBold
import com.egoriku.grodnoroads.foundation.icons.colored.MobileCamera
import com.egoriku.grodnoroads.foundation.icons.colored.MobileCameraBold
import com.egoriku.grodnoroads.foundation.icons.colored.RoadIncident
import com.egoriku.grodnoroads.foundation.icons.colored.RoadIncidentBold
import com.egoriku.grodnoroads.foundation.icons.colored.StationaryCamera
import com.egoriku.grodnoroads.foundation.icons.colored.StationaryCameraBold
import com.egoriku.grodnoroads.foundation.icons.colored.Telegram
import com.egoriku.grodnoroads.foundation.icons.colored.TrafficJam
import com.egoriku.grodnoroads.foundation.icons.colored.TrafficPolice
import com.egoriku.grodnoroads.foundation.icons.colored.TrafficPoliceBold
import com.egoriku.grodnoroads.foundation.icons.colored.Viber
import com.egoriku.grodnoroads.foundation.icons.colored.WildAnimals
import com.egoriku.grodnoroads.foundation.icons.outlined.Add
import com.egoriku.grodnoroads.foundation.icons.outlined.AddCircle
import com.egoriku.grodnoroads.foundation.icons.outlined.Appearance
import com.egoriku.grodnoroads.foundation.icons.outlined.Arrow
import com.egoriku.grodnoroads.foundation.icons.outlined.ArrowLeft
import com.egoriku.grodnoroads.foundation.icons.outlined.ArrowRight
import com.egoriku.grodnoroads.foundation.icons.outlined.Brightness
import com.egoriku.grodnoroads.foundation.icons.outlined.Car
import com.egoriku.grodnoroads.foundation.icons.outlined.Changelog
import com.egoriku.grodnoroads.foundation.icons.outlined.Chat
import com.egoriku.grodnoroads.foundation.icons.outlined.Check
import com.egoriku.grodnoroads.foundation.icons.outlined.CheckCircle
import com.egoriku.grodnoroads.foundation.icons.outlined.ChevronRight
import com.egoriku.grodnoroads.foundation.icons.outlined.Close
import com.egoriku.grodnoroads.foundation.icons.outlined.Error
import com.egoriku.grodnoroads.foundation.icons.outlined.Faq
import com.egoriku.grodnoroads.foundation.icons.outlined.Filter
import com.egoriku.grodnoroads.foundation.icons.outlined.Geo
import com.egoriku.grodnoroads.foundation.icons.outlined.InsideCity
import com.egoriku.grodnoroads.foundation.icons.outlined.Language
import com.egoriku.grodnoroads.foundation.icons.outlined.Map
import com.egoriku.grodnoroads.foundation.icons.outlined.Moon
import com.egoriku.grodnoroads.foundation.icons.outlined.More
import com.egoriku.grodnoroads.foundation.icons.outlined.MyCity
import com.egoriku.grodnoroads.foundation.icons.outlined.Notification
import com.egoriku.grodnoroads.foundation.icons.outlined.NotificationBadge
import com.egoriku.grodnoroads.foundation.icons.outlined.Ok
import com.egoriku.grodnoroads.foundation.icons.outlined.OutsideCity
import com.egoriku.grodnoroads.foundation.icons.outlined.PinLocation
import com.egoriku.grodnoroads.foundation.icons.outlined.PinMarker
import com.egoriku.grodnoroads.foundation.icons.outlined.Play
import com.egoriku.grodnoroads.foundation.icons.outlined.Remove
import com.egoriku.grodnoroads.foundation.icons.outlined.RemoveCircle
import com.egoriku.grodnoroads.foundation.icons.outlined.Settings
import com.egoriku.grodnoroads.foundation.icons.outlined.Share
import com.egoriku.grodnoroads.foundation.icons.outlined.Telegram
import com.egoriku.grodnoroads.foundation.icons.outlined.TrafficJam
import com.egoriku.grodnoroads.foundation.icons.outlined.Truck
import com.egoriku.grodnoroads.foundation.icons.outlined.Undo
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview

@Preview
@Composable
private fun GrodnoRoadsColoredPackPreview() = GrodnoRoadsM3ThemePreview {
    val icons = listOf(
        GrodnoRoads.Colored.App,
        GrodnoRoads.Colored.AppDark,
        GrodnoRoads.Colored.CarCrash,
        GrodnoRoads.Colored.Info,
        GrodnoRoads.Colored.MediumSpeedCamera,
        GrodnoRoads.Colored.MediumSpeedCameraBold,
        GrodnoRoads.Colored.MobileCamera,
        GrodnoRoads.Colored.MobileCameraBold,
        GrodnoRoads.Colored.RoadIncident,
        GrodnoRoads.Colored.RoadIncidentBold,
        GrodnoRoads.Colored.StationaryCamera,
        GrodnoRoads.Colored.StationaryCameraBold,
        GrodnoRoads.Colored.Telegram,
        GrodnoRoads.Colored.TrafficJam,
        GrodnoRoads.Colored.TrafficPolice,
        GrodnoRoads.Colored.TrafficPoliceBold,
        GrodnoRoads.Colored.Viber,
        GrodnoRoads.Colored.WildAnimals
    )

    LazyVerticalGrid(
        modifier = Modifier.padding(vertical = 8.dp),
        columns = GridCells.Adaptive(minSize = 64.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(icons) { icon ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Image(
                    imageVector = icon,
                    contentDescription = icon.name
                )
                Text(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    text = icon.name.removePrefix("Colored."),
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 9.sp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview
@Composable
private fun GrodnoRoadsOutlinedPackPreview() = GrodnoRoadsM3ThemePreview {
    val icons = listOf(
        GrodnoRoads.Outlined.Add,
        GrodnoRoads.Outlined.AddCircle,
        GrodnoRoads.Outlined.Appearance,
        GrodnoRoads.Outlined.Arrow,
        GrodnoRoads.Outlined.ArrowLeft,
        GrodnoRoads.Outlined.ArrowRight,
        GrodnoRoads.Outlined.Brightness,
        GrodnoRoads.Outlined.Car,
        GrodnoRoads.Outlined.Changelog,
        GrodnoRoads.Outlined.Chat,
        GrodnoRoads.Outlined.Check,
        GrodnoRoads.Outlined.CheckCircle,
        GrodnoRoads.Outlined.ChevronRight,
        GrodnoRoads.Outlined.Close,
        GrodnoRoads.Outlined.Error,
        GrodnoRoads.Outlined.Faq,
        GrodnoRoads.Outlined.Filter,
        GrodnoRoads.Outlined.Geo,
        GrodnoRoads.Outlined.InsideCity,
        GrodnoRoads.Outlined.Language,
        GrodnoRoads.Outlined.Map,
        GrodnoRoads.Outlined.Moon,
        GrodnoRoads.Outlined.More,
        GrodnoRoads.Outlined.MyCity,
        GrodnoRoads.Outlined.Notification,
        GrodnoRoads.Outlined.NotificationBadge,
        GrodnoRoads.Outlined.Ok,
        GrodnoRoads.Outlined.OutsideCity,
        GrodnoRoads.Outlined.PinLocation,
        GrodnoRoads.Outlined.PinMarker,
        GrodnoRoads.Outlined.Play,
        GrodnoRoads.Outlined.Remove,
        GrodnoRoads.Outlined.RemoveCircle,
        GrodnoRoads.Outlined.Settings,
        GrodnoRoads.Outlined.Share,
        GrodnoRoads.Outlined.Telegram,
        GrodnoRoads.Outlined.TrafficJam,
        GrodnoRoads.Outlined.Truck,
        GrodnoRoads.Outlined.Undo
    )

    LazyVerticalGrid(
        modifier = Modifier.padding(vertical = 8.dp),
        columns = GridCells.Adaptive(minSize = 64.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(icons) { icon ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = icon.name
                )
                Text(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    text = icon.name.removePrefix("Outlined."),
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 9.sp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
