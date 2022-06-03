package com.egoriku.grodnoroads.screen.main.ui.drawer.section

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.foundation.drawer.DrawerNavigationItem
import com.egoriku.grodnoroads.ui.theme.GrodnoRoadsTheme

@Composable
fun ShareAppSection(onClick: (String) -> Unit) {
    val url = stringResource(R.string.play_store_link)

    DrawerNavigationItem(
        text = stringResource(R.string.drawer_share_app_section),
        icon = {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Default.Share,
                contentDescription = null
            )
        },
        onClick = { onClick(url) }
    )
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true, locale = "ru")
@Composable
private fun ShareAppSectionPreview() {
    GrodnoRoadsTheme {
        Surface {
            ShareAppSection(onClick = {})
        }
    }
}