package com.egoriku.grodnoroads.cityselector.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.cityselector.domain.component.CitySelectorComponent.CitySelectorPref
import com.egoriku.grodnoroads.cityselector.domain.component.CitySelectorComponent.CitySelectorPref.DefaultCity
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.city_selector_choose_city
import com.egoriku.grodnoroads.extensions.Collator
import com.egoriku.grodnoroads.foundation.common.ui.lazycolumn.SingleChoiceLazyColumn
import com.egoriku.grodnoroads.foundation.uikit.VerticalSpacer
import com.egoriku.grodnoroads.shared.persistent.map.location.City
import com.egoriku.grodnoroads.shared.persistent.toStringResource
import kotlinx.collections.immutable.toImmutableList
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ChooseCityPage(
    defaultCity: DefaultCity,
    modifier: Modifier = Modifier,
    onModify: (CitySelectorPref) -> Unit
) {
    val sortedCityValues = defaultCity.values
        .mapIndexed { index, value ->
            CityValue(index, stringResource(value.toStringResource()))
        }
        .sortedWith(compareBy(Collator.collator) { it.value })

    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = stringResource(Res.string.city_selector_choose_city),
            style = MaterialTheme.typography.displaySmall,
            textAlign = TextAlign.Center
        )
        VerticalSpacer(24.dp)
        SingleChoiceLazyColumn(
            modifier = Modifier.fillMaxWidth(),
            list = sortedCityValues.map { it.value }.toImmutableList(),
            initialSelection = sortedCityValues.indexOfFirst { cityValue ->
                cityValue.index == City.entries.indexOf(City.Grodno)
            },
            onSelect = { position ->
                onModify(defaultCity.copy(current = defaultCity.values[sortedCityValues[position].index]))
            }
        )
    }
}

private data class CityValue(
    val index: Int,
    val value: String
)
