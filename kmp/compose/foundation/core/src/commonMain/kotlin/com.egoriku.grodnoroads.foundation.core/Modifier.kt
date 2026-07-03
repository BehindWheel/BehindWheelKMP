package com.egoriku.grodnoroads.foundation.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun Modifier.unboundClickable(onClick: () -> Unit): Modifier {
    return this then Modifier.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = ripple(bounded = false),
        onClick = onClick
    )
}

inline fun Modifier.thenIf(
    condition: Boolean,
    modifier: Modifier.() -> Modifier
): Modifier = when {
    condition -> then(modifier(Modifier))
    else -> this
}
