package com.egoriku.grodnoroads.screen.settings.map.ui.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.egoriku.grodnoroads.foundation.dialog.ListSingleChoiceDialog
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapDialogState.DefaultLocationDialogState
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.DefaultCity
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.DefaultCity.City.Companion.toResource

@Composable
fun DefaultLocationDialog(
    defaultLocationState: DefaultLocationDialogState,
    onClose: () -> Unit,
    onResult: (MapPref) -> Unit
) {
    val defaultCity = defaultLocationState.defaultCity

    ListSingleChoiceDialog(
        list = defaultCity.values.map {
            stringResource(id = it.toResource())
        },
        initialSelection = defaultCity.values.indexOf(defaultCity.current),
        onClose = onClose,
        onSelected = { position ->
            onResult(defaultCity.copy(current = defaultCity.values[position]))
        }
    )
}

@Preview
@Preview(locale = "ru")
@Composable
private fun PreviewDefaultLocationDialog() {
    DefaultLocationDialog(
        defaultLocationState = DefaultLocationDialogState(defaultCity = DefaultCity()),
        onClose = {},
        onResult = {}
    )
}