package com.egoriku.grodnoroads.screen.main.ui.drawer.section

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.ui.tooling.preview.Preview
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.ui.theme.GrodnoRoadsTheme

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

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true, locale = "ru")
@Composable
private fun ShareAppSectionPreview() {
    GrodnoRoadsTheme {
        ShareAppSection(onClick = {})
    }
}