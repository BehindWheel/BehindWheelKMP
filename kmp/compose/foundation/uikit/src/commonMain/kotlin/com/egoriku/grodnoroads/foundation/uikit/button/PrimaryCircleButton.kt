package com.egoriku.grodnoroads.foundation.uikit.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.uikit.button.common.Size
import com.egoriku.grodnoroads.foundation.uikit.button.common.Size.Large
import com.egoriku.grodnoroads.foundation.uikit.button.common.Size.Small
import com.egoriku.grodnoroads.shared.resources.MR
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun PrimaryCircleButton(
    onClick: () -> Unit,
    size: Size,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    val buttonSize = when (size) {
        Large -> 56.dp
        Small -> 48.dp
    }
    Button(
        contentPadding = PaddingValues(0.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
        modifier = modifier.size(buttonSize),
        enabled = enabled,
        onClick = onClick,
        content = content
    )
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun PrimaryCircleButtonPreview() = GrodnoRoadsM3ThemePreview {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PrimaryCircleButton(size = Large, onClick = {}) {
            Icon(
                painter = painterResource(MR.images.ic_arrow_right),
                contentDescription = null
            )
        }
        PrimaryCircleButton(size = Small, onClick = {}) {
            Icon(
                painter = painterResource(MR.images.ic_arrow_right),
                contentDescription = null
            )
        }

    }
}