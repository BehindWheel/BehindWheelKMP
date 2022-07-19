package com.egoriku.grodnoroads.screen.main

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.egoriku.grodnoroads.screen.main.MainComponent.Child
import com.egoriku.grodnoroads.screen.main.ui.DrawerContent
import com.egoriku.grodnoroads.screen.map.MapUi
import com.egoriku.grodnoroads.screen.settings.SettingsUi
import kotlinx.coroutines.launch

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun MainUi(component: MainComponent) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val openDrawer: () -> Unit = {
        scope.launch {
            drawerState.open()
        }
    }

    ModalDrawer(
        gesturesEnabled = drawerState.isOpen,
        drawerState = drawerState,
        drawerShape = RoundedCornerShape(0),
        drawerContent = {
            DrawerContent(modifier = Modifier.systemBarsPadding())
        }
    ) {
        val childStack by component.childStack.subscribeAsState()

        Scaffold(
            modifier = Modifier.navigationBarsPadding(),
            content = {
                Children(
                    modifier = Modifier.padding(it),
                    stack = childStack,
                    animation = stackAnimation(fade())
                ) { created ->
                    when (val child = created.instance) {
                        is Child.Map -> MapUi(
                            component = child.component,
                            openDrawer = openDrawer
                        )
                        is Child.Settings -> SettingsUi(settingsComponent = child.component)
                    }
                }
            },
            bottomBar = {
                BottomNavigation {
                    listOf(Screen.Map(), Screen.Settings()).forEach { screen ->
                        BottomNavigationItem(
                            selected = screen.index == childStack.active.instance.index,
                            onClick = { component.onSelectTab(index = screen.index) },
                            icon = {
                                Icon(
                                    painter = rememberVectorPainter(image = screen.icon),
                                    contentDescription = null
                                )
                            },
                            label = {
                                Text(text = stringResource(id = screen.labelId))
                            }
                        )
                    }
                }
            }
        )
    }
}