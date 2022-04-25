package com.egoriku.grodnoroads.ui.mode.drive.action

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    IconButton(
        onClick = onClick,
        modifier = modifier.background(Color.White, RoundedCornerShape(10.dp))
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