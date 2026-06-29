package com.egoriku.grodnoroads.guidance.screen.ui.foundation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.map_user_count
import com.egoriku.grodnoroads.compose.resources.map_user_count_hint
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.colored.Info
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoads
import com.egoriku.grodnoroads.foundation.theme.isLight
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersCount(count: Int, modifier: Modifier = Modifier) {
    val tooltipState = rememberTooltipState(isPersistent = true)
    val scope = rememberCoroutineScope()

    Box(modifier = modifier) {
        TooltipBox(
            focusable = true,
            positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
                positioning = TooltipAnchorPosition.Above,
                spacingBetweenTooltipAndAnchor = 8.dp
            ),
            tooltip = {
                PlainTooltip(
                    caretShape = TooltipDefaults.caretShape(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        style = MaterialTheme.typography.bodySmall,
                        text = stringResource(Res.string.map_user_count_hint)
                    )
                }
            },
            state = tooltipState
        ) {
            UsersCountBadge(
                onClick = {
                    scope.launch {
                        tooltipState.show()
                    }
                },
                count = count
            )
        }
    }
}

@Composable
fun UsersCountBadge(
    count: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
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
            )
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier.padding(end = 3.dp)
        ) {
            Image(
                modifier = Modifier.size(16.dp),
                imageVector = GrodnoRoads.Colored.Info,
                contentDescription = null
            )
            Text(
                text = stringResource(Res.string.map_user_count, count),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@PreviewGrodnoRoads
@Composable
private fun UsersCountBadgePreview() = GrodnoRoadsM3ThemePreview {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        UsersCount(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            count = 10
        )
    }
}
