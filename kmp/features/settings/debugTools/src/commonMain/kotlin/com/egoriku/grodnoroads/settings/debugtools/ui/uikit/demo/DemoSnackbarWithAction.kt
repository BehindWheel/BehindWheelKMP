package com.egoriku.grodnoroads.settings.debugtools.ui.uikit.demo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.egoriku.grodnoroads.compose.snackbar.model.MessageData
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarMessage
import com.egoriku.grodnoroads.compose.snackbar.ui.internal.MessageWithActionItem
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import com.egoriku.grodnoroads.settings.debugtools.ui.uikit.common.UIKitDemoContainer

@Composable
internal fun DemoSnackbarWithAction(modifier: Modifier = Modifier) {
    UIKitDemoContainer(modifier = modifier, name = "SnackbarWithAction") {
        MessageWithActionItem(
            message = SnackbarMessage.ActionMessage(
                title = MessageData.Raw("Доступ к геолокации запрещен."),
                description = MessageData.Raw("Используется для доступа к данным карт"),
                onAction = {}
            ),
            onAction = {}
        )
    }
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun DemoSnackbarWithActionPreview() = GrodnoRoadsM3ThemePreview {
    DemoSnackbarWithAction()
}
