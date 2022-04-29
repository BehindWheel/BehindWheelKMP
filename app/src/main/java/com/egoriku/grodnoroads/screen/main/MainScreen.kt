package com.egoriku.grodnoroads.screen.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.egoriku.grodnoroads.screen.chat.ChatScreenTab
import com.egoriku.grodnoroads.screen.main.ui.DrawerContent
import com.egoriku.grodnoroads.screen.map.MapScreenTab
import kotlinx.coroutines.launch

object MainScreen : Screen {

    @Composable
    override fun Content() {
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
                    modifier = Modifier.statusBarsPadding(),
                    navigate = {

                    }
                )
            }
        ) {
            TabNavigator(MapScreenTab(openDrawer = openDrawer)) {
                Scaffold(
                    modifier = Modifier.navigationBarsPadding(),
                    content = {
                        Box(modifier = Modifier.padding(it)) {
                            CurrentTab()
                        }
                    },
                    bottomBar = {
                        BottomNavigation {
                            TabNavigationItem(tab = MapScreenTab(openDrawer = openDrawer))
                            TabNavigationItem(tab = ChatScreenTab)
                        }
                    }
                )
            }
        }
    }

    @Composable
    private fun RowScope.TabNavigationItem(tab: Tab) {
        val tabNavigator = LocalTabNavigator.current

        BottomNavigationItem(
            selected = tabNavigator.current == tab,
            onClick = { tabNavigator.current = tab },
            icon = {
                Icon(painter = tab.options.icon!!, contentDescription = tab.options.title)
            },
            label = {
                Text(text = tab.options.title)
            }
        )
    }
}