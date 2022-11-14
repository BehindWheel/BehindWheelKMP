package com.egoriku.grodnoroads.foundation.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun <T> ListItems(
    modifier: Modifier = Modifier,
    list: List<T>,
    onClick: (index: Int, item: T) -> Unit = { _, _ -> },
    footer: @Composable () -> Unit = {},
    item: @Composable (index: Int, T) -> Unit = { _, _ -> },
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth()
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
