package com.egoriku.grodnoroads.uidemo

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
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
import com.egoriku.grodnoroads.uidemo.ui.demo.*
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
                            lightScrim = Color.TRANSPARENT,
                            darkScrim = Color.TRANSPARENT,
                        ) { isDark },
                        navigationBarStyle = SystemBarStyle.auto(
                            lightScrim = Color.TRANSPARENT,
                            darkScrim = Color.TRANSPARENT,
                        ) { isDark },
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
                            onThemeChange = { isDark = !isDark },
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
