package com.egoriku.grodnoroads.settings.map.ui.bottomsheet

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.lazycolumn.SingleChoiceLazyColumn
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.bottomsheet.BasicModalBottomSheet
import com.egoriku.grodnoroads.foundation.uikit.bottomsheet.common.ConfirmationFooter
import com.egoriku.grodnoroads.foundation.uikit.bottomsheet.rememberSheetCloseBehaviour
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapDialogState.DefaultLocationDialogState
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref.DefaultCity
import com.egoriku.grodnoroads.shared.appsettings.types.map.location.City.Companion.toResource
import kotlinx.collections.immutable.toImmutableList
import java.text.Collator
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DefaultLocationBottomSheet(
    defaultLocationState: DefaultLocationDialogState,
    onCancel: () -> Unit,
    onResult: (MapPref) -> Unit
) {
    var defaultCity by rememberMutableState { defaultLocationState.defaultCity }

    val sheetCloseBehaviour = rememberSheetCloseBehaviour(
        onCancel = onCancel,
        onResult = { onResult(defaultCity) }
    )

    BasicModalBottomSheet(
        sheetState = sheetCloseBehaviour.sheetState,
        onCancel = onCancel,
        content = {
            val sortedCityValues = defaultCity.values
                .mapIndexed { index, value ->
                    CityValue(index, stringResource(id = value.toResource()))
                }.sortedWith(compareBy(Collator.getInstance(Locale.getDefault())) { it.value })

            SingleChoiceLazyColumn(
                list = sortedCityValues.map { it.value }.toImmutableList(),
                contentPadding = PaddingValues(bottom = 16.dp),
                initialSelection = sortedCityValues.indexOfFirst { cityValue ->
                    cityValue.index == defaultCity.values.indexOf(defaultCity.current)
                },
                onSelected = { position ->
                    defaultCity =
                        defaultCity.copy(current = defaultCity.values[sortedCityValues[position].index])
                }
            )
            Divider()
        },
        footer = {
            ConfirmationFooter(
                onDismiss = sheetCloseBehaviour::cancel,
                onAccept = sheetCloseBehaviour::hideWithResult
            )
        }
    )
}

private data class CityValue(
    val index: Int,
    val value: String
)


@GrodnoRoadsPreview
@Composable
private fun PreviewDefaultLocationBottomSheet() = GrodnoRoadsM3ThemePreview {
    DefaultLocationBottomSheet(
        defaultLocationState = DefaultLocationDialogState(defaultCity = DefaultCity()),
        onCancel = {},
        onResult = {}
    )
}