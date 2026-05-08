package com.egoriku.grodnoroads.foundation.preview

import androidx.compose.ui.tooling.preview.AndroidUiModes
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Preview(showBackground = true, locale = "ru")
@Preview(showBackground = true, locale = "be")
@Preview(uiMode = AndroidUiModes.UI_MODE_NIGHT_YES, showBackground = true)
annotation class PreviewGrodnoRoads

@Preview(showBackground = true)
@Preview(uiMode = AndroidUiModes.UI_MODE_NIGHT_YES, showBackground = true)
annotation class PreviewGrodnoRoadsDarkLight
