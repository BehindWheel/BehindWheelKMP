package com.egoriku.grodnoroads.settings.debugtools.ui.uikit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.settings.debugtools.ui.uikit.demo.DemoActionButton
import com.egoriku.grodnoroads.settings.debugtools.ui.uikit.demo.DemoActionButtonGroup
import com.egoriku.grodnoroads.settings.debugtools.ui.uikit.demo.DemoCheckbox
import com.egoriku.grodnoroads.settings.debugtools.ui.uikit.demo.DemoClickableRange
import com.egoriku.grodnoroads.settings.debugtools.ui.uikit.demo.DemoFilterChip
import com.egoriku.grodnoroads.settings.debugtools.ui.uikit.demo.DemoListItem
import com.egoriku.grodnoroads.settings.debugtools.ui.uikit.demo.DemoNavigationBar
import com.egoriku.grodnoroads.settings.debugtools.ui.uikit.demo.DemoNavigationRail
import com.egoriku.grodnoroads.settings.debugtools.ui.uikit.demo.DemoPrimaryButton
import com.egoriku.grodnoroads.settings.debugtools.ui.uikit.demo.DemoPrimaryCircleButton
import com.egoriku.grodnoroads.settings.debugtools.ui.uikit.demo.DemoPrimaryInverseCircleButton
import com.egoriku.grodnoroads.settings.debugtools.ui.uikit.demo.DemoRadioButton
import com.egoriku.grodnoroads.settings.debugtools.ui.uikit.demo.DemoSecondaryButton
import com.egoriku.grodnoroads.settings.debugtools.ui.uikit.demo.DemoSecondaryCircleButton
import com.egoriku.grodnoroads.settings.debugtools.ui.uikit.demo.DemoSnackbarSimple
import com.egoriku.grodnoroads.settings.debugtools.ui.uikit.demo.DemoSnackbarWithAction
import com.egoriku.grodnoroads.settings.debugtools.ui.uikit.demo.DemoSwitch
import com.egoriku.grodnoroads.settings.debugtools.ui.uikit.demo.DemoText
import com.egoriku.grodnoroads.settings.debugtools.ui.uikit.demo.DemoTextButton
import com.egoriku.grodnoroads.settings.debugtools.ui.uikit.demo.DemoTriStateCheckbox

@Composable
internal fun UiDemoList(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DemoSwitch()
        DemoCheckbox()
        DemoTriStateCheckbox()
        DemoRadioButton()
        DemoText()
        DemoPrimaryButton()
        DemoPrimaryInverseCircleButton()
        DemoPrimaryCircleButton()
        DemoSecondaryButton()
        DemoSecondaryCircleButton()
        DemoTextButton()
        DemoActionButton()
        DemoActionButtonGroup()
        DemoClickableRange()
        DemoFilterChip()
        DemoNavigationRail()
        DemoNavigationBar()
        DemoListItem()
        DemoSnackbarSimple()
        DemoSnackbarWithAction()
    }
}

@Preview(heightDp = 3000)
@Composable
private fun UiDemoListPreview() = GrodnoRoadsM3ThemePreview {
    UiDemoList(modifier = Modifier.padding(16.dp))
}
