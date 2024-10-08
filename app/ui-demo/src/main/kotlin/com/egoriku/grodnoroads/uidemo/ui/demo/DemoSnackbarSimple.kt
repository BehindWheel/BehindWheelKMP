package com.egoriku.grodnoroads.uidemo.ui.demo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.egoriku.grodnoroads.compose.snackbar.model.MessageData
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarMessage
import com.egoriku.grodnoroads.compose.snackbar.ui.internal.SimpleMessageItem
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import com.egoriku.grodnoroads.uidemo.ui.UIDemoContainer

@Composable
fun DemoSnackbarSimple(modifier: Modifier = Modifier) {
    UIDemoContainer(modifier = modifier, name = "SnackbarSimple") {
        SimpleMessageItem(
            message = SnackbarMessage.SimpleMessage(
                title = MessageData.Raw("Доступ к геолокации запрещен."),
                description = MessageData.Raw("Используется для доступа к данным карт")
            )
        )
    }
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun DemoCheckboxPreview() = GrodnoRoadsM3ThemePreview {
    DemoSnackbarSimple()
}
