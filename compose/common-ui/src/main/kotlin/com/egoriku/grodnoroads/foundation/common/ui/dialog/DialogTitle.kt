package com.egoriku.grodnoroads.foundation.common.ui.dialog

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.resources.MR

@Composable
fun DialogTitle(
    @StringRes titleRes: Int,
    center: Boolean = false
) {
    val titleText = stringResource(titleRes)

    var modifier = Modifier
        .fillMaxWidth()
        .padding(start = 24.dp, end = 24.dp)
        .height(64.dp)
        .wrapContentHeight(Alignment.CenterVertically)

    modifier = modifier.then(
        Modifier.wrapContentWidth(
            if (center) {
                Alignment.CenterHorizontally
            } else {
                Alignment.Start
            }
        )
    )

    Text(
        modifier = modifier,
        text = titleText,
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.titleLarge
    )
}

@GrodnoRoadsPreview
@Composable
private fun PreviewDialogTitle() = GrodnoRoadsM3ThemePreview {
    Column(modifier = Modifier.fillMaxWidth()) {
        DialogTitle(titleRes = MR.strings.app_name.resourceId)
        HorizontalDivider()
        DialogTitle(titleRes = MR.strings.app_name.resourceId, center = true)
    }
}