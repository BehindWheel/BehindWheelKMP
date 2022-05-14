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
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.animation.child.childAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.animation.child.fade
import com.arkivanov.decompose.extensions.compose.jetpack.animation.child.plus
import com.arkivanov.decompose.extensions.compose.jetpack.animation.child.scale
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.egoriku.grodnoroads.screen.chat.ChatUi
import com.egoriku.grodnoroads.screen.main.MainComponent.Child
import com.egoriku.grodnoroads.screen.main.ui.DrawerContent
import com.egoriku.grodnoroads.screen.map.MapUi
import kotlinx.coroutines.launch

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun MainUi(component: MainComponent, openSettings: () -> Unit) {
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
            DrawerContent(
                modifier = Modifier.systemBarsPadding(),
                navigate = {
                    openSettings()
                }
            )
        }
    ) {
        Scaffold(
            modifier = Modifier.navigationBarsPadding(),
            content = {
                Children(
                    modifier = Modifier.padding(it),
                    routerState = component.routerState,
                    animation = childAnimation(scale() + fade())
                ) { created ->
                    when (val child = created.instance) {
                        is Child.Map -> MapUi(
                            component = child.component,
                            openDrawer = openDrawer
                        )
                        is Child.Chat -> ChatUi()
                    }
                }
            },
            bottomBar = {
                val activeIndex by component.activeChildIndex.subscribeAsState()

                BottomNavigation {
                    listOf(
                        Screen.Map(),
                        // FsScreen.Chat()
                    ).forEach { screen ->
                        BottomNavigationItem(
                            selected = screen.index == activeIndex,
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