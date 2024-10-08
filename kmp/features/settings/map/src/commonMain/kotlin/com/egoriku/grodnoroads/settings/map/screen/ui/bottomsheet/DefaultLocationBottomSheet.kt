package com.egoriku.grodnoroads.settings.map.screen.ui.bottomsheet

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.extensions.Collator
import com.egoriku.grodnoroads.foundation.common.ui.bottomsheet.BasicModalBottomSheet
import com.egoriku.grodnoroads.foundation.common.ui.bottomsheet.common.ConfirmationFooter
import com.egoriku.grodnoroads.foundation.common.ui.bottomsheet.rememberSheetCloseBehaviour
import com.egoriku.grodnoroads.foundation.common.ui.lazycolumn.SingleChoiceLazyColumn
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoads
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapDialogState.DefaultLocationDialogState
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref.DefaultCity
import com.egoriku.grodnoroads.shared.persistent.toStringResource
import kotlinx.collections.immutable.toImmutableList
import org.jetbrains.compose.resources.stringResource

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
                    CityValue(index, stringResource(value.toStringResource()))
                }.sortedWith(compareBy(Collator.collator) { it.value })

            SingleChoiceLazyColumn(
                list = sortedCityValues.map { it.value }.toImmutableList(),
                contentPadding = PaddingValues(bottom = 16.dp),
                initialSelection = sortedCityValues.indexOfFirst { cityValue ->
                    cityValue.index == defaultCity.values.indexOf(defaultCity.current)
                },
                onSelect = { position ->
                    defaultCity =
                        defaultCity.copy(current = defaultCity.values[sortedCityValues[position].index])
                }
            )
            HorizontalDivider()
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

@PreviewGrodnoRoads
@Composable
private fun PreviewDefaultLocationBottomSheetPreview() = GrodnoRoadsM3ThemePreview {
    DefaultLocationBottomSheet(
        defaultLocationState = DefaultLocationDialogState(defaultCity = DefaultCity()),
        onCancel = {},
        onResult = {}
    )
}
