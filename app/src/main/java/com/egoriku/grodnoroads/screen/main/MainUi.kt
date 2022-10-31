package com.egoriku.grodnoroads.screen.main

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.egoriku.grodnoroads.foundation.animation.VerticalSlideAnimatedVisibility
import com.egoriku.grodnoroads.foundation.bottombar.BottomBarVisibilityState
import com.egoriku.grodnoroads.foundation.bottombar.LocalBottomBarVisibilityController
import com.egoriku.grodnoroads.map.MapScreen
import com.egoriku.grodnoroads.screen.main.MainComponent.Child
import com.egoriku.grodnoroads.screen.main.ui.drawer.DrawerContent
import com.egoriku.grodnoroads.screen.settings.SettingsScreen
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
        val bottomNavItems = listOf(Screen.Map, Screen.Settings)

        val childStack by component.childStack.subscribeAsState()

        var bottomBarVisibility by rememberSaveable { mutableStateOf(BottomBarVisibilityState.SHOWN) }
        val onChangeBottomBarVisibility = remember { { visibility: BottomBarVisibilityState -> bottomBarVisibility = visibility } }

        Scaffold(
            modifier = Modifier.navigationBarsPadding(),
            content = {
                CompositionLocalProvider(
                    LocalBottomBarVisibilityController provides onChangeBottomBarVisibility
                ) {
                    Children(
                        modifier = Modifier.padding(it),
                        stack = childStack,
                    ) { created ->
                        when (val child = created.instance) {
                            is Child.Map -> MapScreen(
                                component = child.component,
                                openDrawer = openDrawer
                            )
                            is Child.Settings -> SettingsScreen(settingsComponent = child.component)
                        }
                    }
                }
            },
            bottomBar = {
                VerticalSlideAnimatedVisibility(
                    visible = bottomBarVisibility.isShown()
                ) {
                    BottomNavigation {
                        bottomNavItems.forEach { screen ->
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
            }
        )
    }
}