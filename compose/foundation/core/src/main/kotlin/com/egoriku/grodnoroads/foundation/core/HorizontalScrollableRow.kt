package com.egoriku.grodnoroads.foundation.core

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalScrollableRow(
    paddingValues: PaddingValues = PaddingValues(horizontal = 20.dp),
    horizontalArrangement: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(8.dp),
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(paddingValues),
        horizontalArrangement = horizontalArrangement,
        content = content
    )
}
