package com.egoriku.grodnoroads.screen.main.ui.drawer.section

import androidx.compose.foundation.clickable
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsTheme
import com.egoriku.grodnoroads.resources.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShareAppSection(onClick: (String) -> Unit) {
    val url = stringResource(R.string.play_store_link)

    ListItem(
        modifier = Modifier.clickable { onClick(url) },
        text = {
            Text(text = stringResource(R.string.drawer_share_app_section))
        },
        icon = {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = null
            )
        },
    )
}

@GrodnoRoadsPreview
@Composable
private fun ShareAppSectionPreview() {
    GrodnoRoadsTheme {
        ShareAppSection(onClick = {})
    }
}