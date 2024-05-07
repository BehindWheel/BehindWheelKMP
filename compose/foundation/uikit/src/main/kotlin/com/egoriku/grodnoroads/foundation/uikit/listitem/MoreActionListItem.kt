package com.egoriku.grodnoroads.foundation.uikit.listitem

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.shared.resources.MR
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun MoreActionListItem(
    @DrawableRes iconRes: Int? = null,
    text: String,
    value: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .heightIn(min = 48.dp)
            .padding(start = 20.dp, end = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (iconRes != null) {
            Icon(
                painter = painterResource(iconRes),
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                contentDescription = null
            )
        }
        Text(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .weight(1f),
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(text = value, style = MaterialTheme.typography.titleSmall)
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(MR.images.ic_chevron_right),
                contentDescription = null,
            )
        }
    }
}

@GrodnoRoadsPreview
@Composable
private fun MoreActionListItemPreview() = GrodnoRoadsM3ThemePreview {
    MoreActionListItem(
        iconRes = MR.images.ic_my_city.drawableResId,
        text = "My city",
        value = "Grodno",
        onClick = {}
    )
}