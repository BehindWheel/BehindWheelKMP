package com.egoriku.grodnoroads.foundation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun VerticalSpacer(dp: Dp) = Spacer(modifier = Modifier.height(dp))

@Composable
fun HorizontalSpacer(dp: Dp) = Spacer(modifier = Modifier.width(dp))