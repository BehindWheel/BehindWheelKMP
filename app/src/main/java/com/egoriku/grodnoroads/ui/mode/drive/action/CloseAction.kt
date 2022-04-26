package com.egoriku.grodnoroads.ui.mode.drive.action

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.R

@Composable
fun CloseAction(
    modifier: Modifier = Modifier,
    painter: Painter,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .background(Color.White, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple()
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = painter,
            contentDescription = ""
        )
    }
}

@Preview
@Composable
fun CloseActionPreview() {
    CloseAction(painter = painterResource(id = R.drawable.ic_close)) {}
}