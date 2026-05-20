package com.egoriku.grodnoroads.foundation.common.ui.lazycolumn

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.common.ui.dialog.ListItems
import com.egoriku.grodnoroads.foundation.core.rememberMutableIntState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PlatformPreviewProvider
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import com.egoriku.grodnoroads.foundation.theme.Platform
import com.egoriku.grodnoroads.foundation.uikit.listitem.RadioButtonListItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun SingleChoiceLazyColumn(
    list: ImmutableList<String>,
    initialSelection: Int,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    onSelect: (selected: Int) -> Unit
) {
    var selectedItem by rememberMutableIntState { initialSelection }

    ListItems(
        modifier = modifier,
        contentPadding = contentPadding,
        list = list,
        onClick = { index, _ -> selectedItem = index }
    ) { index, item ->
        val selected = remember(selectedItem) { index == selectedItem }

        RadioButtonListItem(
            text = item,
            selected = selected,
            onClick = {
                selectedItem = index
                onSelect(index)
            }
        )
        HorizontalDivider()
    }
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun PreviewListSingleChoiceDialogPreview(
    @PreviewParameter(PlatformPreviewProvider::class) platform: Platform
) = GrodnoRoadsM3ThemePreview(platform = platform) {
    SingleChoiceLazyColumn(
        modifier = Modifier.padding(vertical = 8.dp),
        list = listOf("System", "Dark", "Light").toImmutableList(),
        initialSelection = 0,
        onSelect = {}
    )
}
