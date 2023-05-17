package com.egoriku.grodnoroads.settings.map.ui.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.egoriku.grodnoroads.foundation.dialog.ListSingleChoiceDialog
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsTheme
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapDialogState.DefaultLocationDialogState
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref.DefaultCity
import com.egoriku.grodnoroads.shared.appsettings.types.map.location.City.Companion.toResource
import java.text.Collator
import java.util.Locale

@Composable
internal fun DefaultLocationDialog(
    defaultLocationState: DefaultLocationDialogState,
    onClose: () -> Unit,
    onResult: (MapPref) -> Unit
) {
    val defaultCity = defaultLocationState.defaultCity

    ListSingleChoiceDialog(
        list = defaultCity.values.map {
            stringResource(id = it.toResource())
        }.sortedWith(Collator.getInstance(Locale.getDefault())),
        initialSelection = defaultCity.values.indexOf(defaultCity.current),
        onClose = onClose,
        onSelected = { position ->
            onResult(defaultCity.copy(current = defaultCity.values[position]))
        }
    )
}

@GrodnoRoadsPreview
@Composable
private fun PreviewDefaultLocationDialog() = GrodnoRoadsTheme {
    DefaultLocationDialog(
        defaultLocationState = DefaultLocationDialogState(defaultCity = DefaultCity()),
        onClose = {},
        onResult = {}
    )
}