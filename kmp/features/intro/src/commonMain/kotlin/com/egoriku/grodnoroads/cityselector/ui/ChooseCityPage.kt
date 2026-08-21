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
import com.egoriku.grodnoroads.foundation.common.ui.lazycolumn.Group
import com.egoriku.grodnoroads.foundation.common.ui.lazycolumn.GroupedSingleChoiceLazyColumn
import com.egoriku.grodnoroads.foundation.layout.Spacer
import com.egoriku.grodnoroads.foundation.uikit.listitem.RadioButtonListItem
import com.egoriku.grodnoroads.shared.persistent.toStringResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ChooseCityPage(
    defaultCity: DefaultCity,
    modifier: Modifier = Modifier,
    onModify: (CitySelectorPref) -> Unit
) {
    val sortedCityValues = defaultCity.values
        .map { it to stringResource(it.toStringResource()) }
        .sortedWith(compareBy(Collator.collator) { it.second })
        .map { it.first }

    val displayGroups = sortedCityValues
        .groupBy { it.region }
        .map { (region, cities) ->
            Group(
                header = stringResource(region.stringResource),
                items = cities
            )
        }

    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = stringResource(Res.string.city_selector_choose_city),
            style = MaterialTheme.typography.displaySmall,
            textAlign = TextAlign.Center
        )
        Spacer(24.dp)
        GroupedSingleChoiceLazyColumn(
            modifier = Modifier.fillMaxWidth(),
            groups = displayGroups,
            initialItem = defaultCity.current,
            autoScroll = false,
            onSelect = { city ->
                onModify(defaultCity.copy(current = city))
            },
            itemContent = { city, isSelected, onClick ->
                RadioButtonListItem(
                    text = stringResource(city.toStringResource()),
                    selected = isSelected,
                    onClick = onClick
                )
            }
        )
    }
}
