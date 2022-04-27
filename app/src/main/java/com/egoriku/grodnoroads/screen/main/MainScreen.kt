package com.egoriku.grodnoroads.screen.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.egoriku.grodnoroads.screen.chat.ChatScreenTab
import com.egoriku.grodnoroads.screen.map.MapScreenTab

object MainScreen : Screen {

    @Composable
    override fun Content() {
        TabNavigator(MapScreenTab) {
            Scaffold(
                modifier = Modifier.navigationBarsPadding(),
                content = {
                    Box(modifier = Modifier.padding(it)) {
                        CurrentTab()
                    }
                },
                bottomBar = {
                    BottomNavigation {
                        TabNavigationItem(tab = MapScreenTab)
                        TabNavigationItem(tab = ChatScreenTab)
                    }
                }
            )
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