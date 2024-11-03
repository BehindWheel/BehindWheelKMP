package com.egoriku.grodnoroads.settings.debugtools.ui.uikit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
internal fun UiDemoList(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DemoSwitch()
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
        DemoRadioButton()
        DemoCheckbox()
        DemoTriStateCheckbox()
        DemoFilterChip()
        DemoNavigationRail()
        DemoNavigationBar()
        DemoSnackbarSimple()
        DemoSnackbarWithAction()
        DemoListItem()
    }
}
