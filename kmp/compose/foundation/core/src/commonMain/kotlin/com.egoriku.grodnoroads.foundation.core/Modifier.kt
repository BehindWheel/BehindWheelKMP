@file:Suppress("ktlint:compose:material-two", "ktlint:compose:modifier-composable-check")

package com.egoriku.grodnoroads.foundation.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun Modifier.unboundClickable(onClick: () -> Unit) =
    this.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = rememberRipple(bounded = false),
        onClick = onClick
    )

@Composable
fun Modifier.noIndicationClick(onClick: () -> Unit = {}): Modifier =
    this.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = onClick
    )
