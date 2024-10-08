package com.egoriku.grodnoroads.uidemo.ui.demo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.colored.TrafficPolice
import com.egoriku.grodnoroads.foundation.icons.outlined.Brightness
import com.egoriku.grodnoroads.foundation.icons.outlined.MyCity
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import com.egoriku.grodnoroads.foundation.uikit.listitem.CheckBoxListItem
import com.egoriku.grodnoroads.foundation.uikit.listitem.MoreActionListItem
import com.egoriku.grodnoroads.foundation.uikit.listitem.SimpleListItem
import com.egoriku.grodnoroads.foundation.uikit.listitem.SwitchListItem
import com.egoriku.grodnoroads.foundation.uikit.listitem.TriStateCheckBoxListItem
import com.egoriku.grodnoroads.uidemo.ui.UIDemoContainer

@Composable
fun DemoListItem(modifier: Modifier = Modifier) {
    UIDemoContainer(modifier = modifier, name = "ListItem") {
        var triState by rememberMutableState { ToggleableState.Off }
        TriStateCheckBoxListItem(
            text = "За рулем | Гродно",
            state = triState,
            onToggle = {
                triState = when (triState) {
                    ToggleableState.Indeterminate -> ToggleableState.Off
                    ToggleableState.Off -> ToggleableState.On
                    ToggleableState.On -> ToggleableState.Indeterminate
                }
            }
        )
        var state by rememberMutableState { true }
        CheckBoxListItem(
            paddingValues = PaddingValues(start = 34.dp),
            text = "ГАИ",
            isChecked = state,
            imageVector = GrodnoRoads.Colored.TrafficPolice,
            iconSize = DpSize(32.dp, 32.dp),
            onCheckedChange = { state = it }
        )
        MoreActionListItem(
            imageVector = GrodnoRoads.Outlined.MyCity,
            text = "My city",
            value = "Grodno",
            onClick = {}
        )
        SimpleListItem(
            imageVector = GrodnoRoads.Outlined.Brightness,
            text = "Test test test",
            onClick = {}
        )

        var isChecked1 by rememberMutableState { false }
        var isChecked2 by rememberMutableState { false }
        var isChecked3 by rememberMutableState { false }

        Column {
            SwitchListItem(
                imageVector = GrodnoRoads.Outlined.Brightness,
                text = "За рулем | Гродно",
                isChecked = isChecked1,
                onCheckedChange = { isChecked1 = it }
            )
            SwitchListItem(
                imageVector = GrodnoRoads.Outlined.Brightness,
                text = "За рулем | Гродно",
                description = "За рулем | Гродно",
                isChecked = isChecked2,
                onCheckedChange = { isChecked2 = it }
            )
            SwitchListItem(
                text = "За рулем | Гродно",
                description = "За рулем | Гродно",
                isChecked = isChecked3,
                onCheckedChange = { isChecked3 = it }
            )
        }
    }
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun DemoListItemPreview() = GrodnoRoadsM3ThemePreview {
    DemoListItem()
}
