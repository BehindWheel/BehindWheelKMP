package com.egoriku.grodnoroads.foundation.uikit.listitem

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.ChevronRight
import com.egoriku.grodnoroads.foundation.icons.outlined.MyCity
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoads

@Composable
fun MoreActionListItem(
    text: String,
    value: String,
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    description: String? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .heightIn(min = 48.dp)
            .padding(start = 20.dp, end = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.Top)
                .padding(top = 12.dp),
            imageVector = imageVector,
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            contentDescription = null
        )
        Column(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .padding(end = 8.dp)
                .weight(1f)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium
            )
            if (description != null) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        lineBreak = LineBreak.Heading
                    ),
                    color = LocalContentColor.current.copy(alpha = 0.64f)
                )
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.Top)
                .minimumInteractiveComponentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = value,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleSmall
            )
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = GrodnoRoads.Outlined.ChevronRight,
                contentDescription = null
            )
        }
    }
}

@PreviewGrodnoRoads
@Composable
private fun MoreActionListItemPreview() = GrodnoRoadsM3ThemePreview {
    MoreActionListItem(
        imageVector = GrodnoRoads.Outlined.MyCity,
        text = "My city",
        value = "Grodno",
        onClick = {}
    )
}
