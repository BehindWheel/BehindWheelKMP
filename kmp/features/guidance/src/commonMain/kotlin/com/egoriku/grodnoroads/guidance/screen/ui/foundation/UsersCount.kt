package com.egoriku.grodnoroads.guidance.screen.ui.foundation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.map_user_count
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.colored.Info
import com.egoriku.grodnoroads.foundation.theme.isLight
import org.jetbrains.compose.resources.stringResource

@Composable
expect fun UsersCount(count: Int, modifier: Modifier = Modifier)

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
            .clickable { onClick() }
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
