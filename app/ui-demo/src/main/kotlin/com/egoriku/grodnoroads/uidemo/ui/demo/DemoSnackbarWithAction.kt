package com.egoriku.grodnoroads.uidemo.ui.demo

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import com.egoriku.grodnoroads.compose.snackbar.model.MessageData
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarMessage
import com.egoriku.grodnoroads.compose.snackbar.ui.internal.MessageWithActionItem
import com.egoriku.grodnoroads.extensions.toast
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.uidemo.ui.UIDemoContainer

@Composable
fun DemoSnackbarWithAction() {
    UIDemoContainer(name = "SnackbarWithAction") {
        val context = LocalContext.current
        val isInPreview = LocalInspectionMode.current

        MessageWithActionItem(
            message = SnackbarMessage.ActionMessage(
                title = MessageData.Raw("Доступ к геолокации запрещен."),
                description = MessageData.Raw("Используется для доступа к данным карт"),
                onAction = {
                    if (!isInPreview) {
                        context.toast("Test action")
                    }
                }
            ),
            onAction = {}
        )
    }
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun DemoSnackbarWithActionPreview() = GrodnoRoadsM3ThemePreview {
    DemoSnackbarWithAction()
}
