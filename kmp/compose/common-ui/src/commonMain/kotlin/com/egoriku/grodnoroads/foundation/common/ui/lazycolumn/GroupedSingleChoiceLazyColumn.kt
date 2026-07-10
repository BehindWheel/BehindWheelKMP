package com.egoriku.grodnoroads.foundation.common.ui.lazycolumn

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PlatformPreviewProvider
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import com.egoriku.grodnoroads.foundation.theme.Platform
import com.egoriku.grodnoroads.foundation.uikit.listitem.RadioButtonListItem

data class Group<T>(
    val header: String,
    val items: List<T>
)

@Composable
fun <T> GroupedSingleChoiceLazyColumn(
    groups: List<Group<T>>,
    initialItem: T,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    autoScroll: Boolean = true,
    onSelect: (T) -> Unit = {},
    itemContent: @Composable (item: T, isSelected: Boolean, onClick: () -> Unit) -> Unit
) {
    var selectedItem by rememberMutableState { initialItem }
    val listState = rememberLazyListState()

    if (autoScroll) {
        LaunchedEffect(Unit) {
            var index = 0
            for (group in groups) {
                index += 1 // stickyHeader
                for (item in group.items) {
                    if (item == selectedItem) {
                        listState.scrollToItem(index - 1)
                        return@LaunchedEffect
                    }
                    index += 1
                }
            }
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = contentPadding,
        state = listState
    ) {
        for (group in groups) {
            stickyHeader(key = "header_${group.header}") {
                Text(
                    text = group.header,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                )
            }
            items(
                items = group.items,
                key = { "${group.header}_$it" }
            ) { item ->
                val isSelected = item == selectedItem

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedItem = item
                            onSelect(item)
                        }
                ) {
                    itemContent(item, isSelected) {
                        selectedItem = item
                        onSelect(item)
                    }
                    HorizontalDivider()
                }
            }
        }
    }
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun GroupedSingleChoiceLazyColumnPreview(
    @PreviewParameter(PlatformPreviewProvider::class) platform: Platform
) = GrodnoRoadsM3ThemePreview(platform = platform) {
    val groups = listOf(
        Group(header = "Group A", items = listOf("Apple", "Avocado")),
        Group(header = "Group B", items = listOf("Banana", "Blueberry"))
    )

    GroupedSingleChoiceLazyColumn(
        groups = groups,
        initialItem = "Banana"
    ) { item, isSelected, onClick ->
        RadioButtonListItem(
            text = item,
            selected = isSelected,
            onClick = onClick
        )
    }
}
