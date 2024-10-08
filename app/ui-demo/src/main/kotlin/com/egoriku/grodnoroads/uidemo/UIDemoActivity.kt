package com.egoriku.grodnoroads.uidemo

import android.graphics.Color as AndroidColor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3Theme
import com.egoriku.grodnoroads.uidemo.ui.Header
import com.egoriku.grodnoroads.uidemo.ui.demo.DemoActionButton
import com.egoriku.grodnoroads.uidemo.ui.demo.DemoActionButtonGroup
import com.egoriku.grodnoroads.uidemo.ui.demo.DemoCheckbox
import com.egoriku.grodnoroads.uidemo.ui.demo.DemoClickableRange
import com.egoriku.grodnoroads.uidemo.ui.demo.DemoFilterChip
import com.egoriku.grodnoroads.uidemo.ui.demo.DemoListItem
import com.egoriku.grodnoroads.uidemo.ui.demo.DemoNavigationBar
import com.egoriku.grodnoroads.uidemo.ui.demo.DemoNavigationRail
import com.egoriku.grodnoroads.uidemo.ui.demo.DemoPrimaryButton
import com.egoriku.grodnoroads.uidemo.ui.demo.DemoPrimaryCircleButton
import com.egoriku.grodnoroads.uidemo.ui.demo.DemoPrimaryInverseCircleButton
import com.egoriku.grodnoroads.uidemo.ui.demo.DemoRadioButton
import com.egoriku.grodnoroads.uidemo.ui.demo.DemoSecondaryButton
import com.egoriku.grodnoroads.uidemo.ui.demo.DemoSecondaryCircleButton
import com.egoriku.grodnoroads.uidemo.ui.demo.DemoSnackbarSimple
import com.egoriku.grodnoroads.uidemo.ui.demo.DemoSnackbarWithAction
import com.egoriku.grodnoroads.uidemo.ui.demo.DemoSwitch
import com.egoriku.grodnoroads.uidemo.ui.demo.DemoText
import com.egoriku.grodnoroads.uidemo.ui.demo.DemoTextButton
import com.egoriku.grodnoroads.uidemo.ui.demo.DemoTriStateCheckbox
import com.egoriku.grodnoroads.uidemo.ui.palette.Material3Palette

class UIDemoActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            var isDark by rememberMutableState { false }
            var isOpenPalette by rememberMutableState { false }

            GrodnoRoadsM3Theme(isDark) {
                DisposableEffect(isDark) {
                    enableEdgeToEdge(
                        statusBarStyle = SystemBarStyle.auto(
                            lightScrim = AndroidColor.TRANSPARENT,
                            darkScrim = AndroidColor.TRANSPARENT
                        ) { isDark },
                        navigationBarStyle = SystemBarStyle.auto(
                            lightScrim = AndroidColor.TRANSPARENT,
                            darkScrim = AndroidColor.TRANSPARENT
                        ) { isDark }
                    )
                    onDispose {}
                }
                Surface {
                    Column {
                        Header(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .statusBarsPadding(),
                            onPalette = { isOpenPalette = true },
                            onThemeChange = { isDark = !isDark }
                        )
                        DemoComponents()
                    }
                }
                if (isOpenPalette) {
                    val bottomPadding =
                        WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

                    ModalBottomSheet(
                        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
                        onDismissRequest = { isOpenPalette = false },
                        windowInsets = WindowInsets(0, 0, 0, 0)
                    ) {
                        Material3Palette(modifier = Modifier.padding(bottom = bottomPadding))
                    }
                }
            }
        }
    }
}

@Composable
private fun DemoComponents() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
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
        DemoSwitch()
        DemoFilterChip()
        DemoNavigationRail()
        DemoNavigationBar()
        DemoSnackbarSimple()
        DemoSnackbarWithAction()
        DemoListItem()
    }
}

@Preview(device = "spec:width=1080px,height=7000px,dpi=440")
@Composable
private fun ComponentsPreview() = GrodnoRoadsM3ThemePreview {
    DemoComponents()
}
