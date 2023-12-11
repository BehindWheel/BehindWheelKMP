package com.egoriku.grodnoroads.foundation.common.ui.lazycolumn

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableIntState
import com.egoriku.grodnoroads.foundation.common.ui.dialog.ListItems
import com.egoriku.grodnoroads.foundation.common.ui.dialog.content.RadioButtonItem
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun SingleChoiceLazyColumn(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    list: ImmutableList<String>,
    initialSelection: Int,
    onSelected: (selected: Int) -> Unit
) {
    var selectedItem by rememberMutableIntState { initialSelection }

    ListItems(
        modifier = modifier,
        contentPadding = contentPadding,
        list = list,
        onClick = { index, _ -> selectedItem = index },
    ) { index, item ->
        val selected = remember(selectedItem) { index == selectedItem }

        RadioButtonItem(
            item = item,
            index = index,
            selected = selected,
            onSelect = {
                selectedItem = index
                onSelected(index)
            }
        )
    }
}

@GrodnoRoadsPreview
@Composable
fun PreviewListSingleChoiceDialog() = GrodnoRoadsM3ThemePreview {
    SingleChoiceLazyColumn(
        list = listOf("System", "Dark", "Light").toImmutableList(),
        contentPadding = PaddingValues(vertical = 16.dp),
        initialSelection = 0,
        onSelected = {}
    )
}