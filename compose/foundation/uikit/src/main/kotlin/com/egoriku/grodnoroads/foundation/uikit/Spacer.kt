package com.egoriku.grodnoroads.foundation.uikit

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun VerticalSpacer(dp: Dp) = Spacer(modifier = Modifier.height(dp))

@Composable
fun HorizontalSpacer(dp: Dp) = Spacer(modifier = Modifier.width(dp))

@Composable
fun RowScope.WeightSpacer(weight: Float = 1f) {
    Spacer(modifier = Modifier.weight(weight))
}

@Composable
fun ColumnScope.WeightSpacer(weight: Float = 1f) {
    Spacer(modifier = Modifier.weight(weight))
}