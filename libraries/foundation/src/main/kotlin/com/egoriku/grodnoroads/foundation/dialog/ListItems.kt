package com.egoriku.grodnoroads.foundation.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.collections.immutable.ImmutableList

@Composable
fun <T> ListItems(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    list: ImmutableList<T>,
    onClick: (index: Int, item: T) -> Unit = { _, _ -> },
    footer: @Composable () -> Unit = {},
    item: @Composable (index: Int, T) -> Unit = { _, _ -> },
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = contentPadding
    ) {
        itemsIndexed(list) { index, it ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        onClick = {
                            onClick(index, it)
                        }
                    )
            ) {
                item(index, it)
            }
        }
        item {
            footer()
        }
    }
}
