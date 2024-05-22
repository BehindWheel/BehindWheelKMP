package com.egoriku.grodnoroads.foundation.uikit.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.theme.isLight
import com.egoriku.grodnoroads.foundation.theme.tonalElevation
import com.egoriku.grodnoroads.shared.resources.MR
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    icon: ImageResource,
    onClick: () -> Unit
) {
    ActionButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null
        )
    }
}


@Composable
private fun ActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
) {
    val shadowColor = when {
        MaterialTheme.colorScheme.isLight -> MaterialTheme.colorScheme.outline
        else -> Color.Black
    }
    Surface(
        modifier = modifier
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(10.dp),
                ambientColor = shadowColor,
                spotColor = shadowColor
            ),
        shape = RoundedCornerShape(10.dp),
        onClick = onClick,
        shadowElevation = 0.dp,
        tonalElevation = MaterialTheme.tonalElevation
    ) {
        Box(modifier = Modifier.padding(8.dp)) {
            icon()
        }
    }
}

@GrodnoRoadsPreview
@Composable
private fun ActionButtonPreview() = GrodnoRoadsM3ThemePreview {
    ActionButton(
        icon = MR.images.ic_geo,
        onClick = {}
    )
}