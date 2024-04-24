package com.egoriku.grodnoroads.mainflow.screen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.router.stack.ChildStack
import com.egoriku.grodnoroads.appsettings.screen.AppSettingsScreen
import com.egoriku.grodnoroads.coroutines.onChild
import com.egoriku.grodnoroads.eventreporting.EventReportingScreen
import com.egoriku.grodnoroads.foundation.core.LocalWindowSizeClass
import com.egoriku.grodnoroads.foundation.core.animation.HorizontalSlideAnimatedVisibility
import com.egoriku.grodnoroads.foundation.core.animation.VerticalSlideAnimatedVisibility
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.uikit.NavigationBar
import com.egoriku.grodnoroads.foundation.uikit.NavigationBarItem
import com.egoriku.grodnoroads.foundation.uikit.NavigationRail
import com.egoriku.grodnoroads.foundation.uikit.NavigationRailItem
import com.egoriku.grodnoroads.guidance.screen.GuidanceScreen
import com.egoriku.grodnoroads.mainflow.TabsComponent
import com.egoriku.grodnoroads.mainflow.TabsComponent.Child
import kotlinx.collections.immutable.persistentListOf

private val NavigationBarHeight: Dp = 80.dp

@Composable
fun TabsScreen(tabsComponent: TabsComponent) {
    val windowSizeClass = LocalWindowSizeClass.current

    val childStack by tabsComponent.childStack.collectAsState()

    Box {
        when (windowSizeClass.widthSizeClass) {
            WindowWidthSizeClass.Expanded -> {
                HorizontalOrientationLayout(
                    childStack = { childStack },
                    onSelectTab = tabsComponent::onSelectTab,
                    activeIndex = { childStack.active.instance.index }
                )
            }
            else -> {
                VerticalOrientationLayout(
                    childStack = { childStack },
                    onSelectTab = tabsComponent::onSelectTab,
                    activeIndex = { childStack.active.instance.index }
                )
            }
        }

        val childSlot by tabsComponent.childSlot.collectAsState()
        childSlot.onChild {
            EventReportingScreen(
                onClose = tabsComponent::closeReporting,
                onReport = tabsComponent::processReporting
            )
        }
    }
}

@Composable
private fun VerticalOrientationLayout(
    childStack: () -> ChildStack<*, Child>,
    activeIndex: () -> Int,
    onSelectTab: (Int) -> Unit,
) {
    val bottomNavItems = remember { persistentListOf(Screen.Map, Screen.AppSettings) }
    var isShowBottomBar by rememberMutableState { true }

    val bottomPadding by animateDpAsState(
        targetValue = if (isShowBottomBar) NavigationBarHeight else 0.dp,
        label = "bottomPadding"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Children(
            modifier = Modifier.fillMaxSize(),
            stack = childStack(),
        ) { created ->
            when (val child = created.instance) {
                is Child.Guidance -> {
                    GuidanceScreen(
                        contentPadding = WindowInsets
                            .systemBars
                            .add(WindowInsets(bottom = bottomPadding))
                            .asPaddingValues(),
                        component = child.component,
                        onBottomNavigationVisibilityChange = { isShowBottomBar = it }
                    )
                }
                is Child.AppSettings -> {
                    AppSettingsScreen(
                        contentPadding = WindowInsets
                            .navigationBars
                            .add(WindowInsets(bottom = bottomPadding))
                            .asPaddingValues(),
                        settingsComponent = child.component
                    )
                }
            }
        }

        VerticalSlideAnimatedVisibility(
            visible = isShowBottomBar,
            modifier = Modifier.align(Alignment.BottomStart)
        ) {
            NavigationBar {
                bottomNavItems.forEach { screen ->
                    NavigationBarItem(
                        selected = screen.index == activeIndex(),
                        onClick = { onSelectTab(screen.index) },
                        icon = {
                            Icon(
                                painter = painterResource(id = screen.iconRes),
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
}

@Composable
private fun HorizontalOrientationLayout(
    childStack: () -> ChildStack<*, Child>,
    activeIndex: () -> Int,
    onSelectTab: (Int) -> Unit,
) {
    val bottomNavItems = remember { persistentListOf(Screen.Map, Screen.AppSettings) }
    var isHideBottomBar by rememberMutableState { true }

    val leftPadding by animateDpAsState(
        targetValue = if (isHideBottomBar) NavigationBarHeight else 0.dp,
        label = "leftPadding"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Children(
            modifier = Modifier.fillMaxSize(),
            stack = childStack(),
        ) { created ->
            when (val child = created.instance) {
                is Child.Guidance ->
                    GuidanceScreen(
                        contentPadding = WindowInsets.systemBars
                            .add(WindowInsets(left = leftPadding))
                            .asPaddingValues(),
                        component = child.component,
                        onBottomNavigationVisibilityChange = { isHideBottomBar = it }
                    )
                is Child.AppSettings -> {
                    AppSettingsScreen(
                        contentPadding = WindowInsets.navigationBars
                            .add(WindowInsets(left = leftPadding))
                            .asPaddingValues(),
                        settingsComponent = child.component
                    )
                }
            }
        }

        HorizontalSlideAnimatedVisibility(isHideBottomBar) {
            NavigationRail {
                bottomNavItems.forEach { screen ->
                    NavigationRailItem(
                        selected = screen.index == activeIndex(),
                        onClick = { onSelectTab(screen.index) },
                        icon = {
                            Icon(
                                painter = painterResource(id = screen.iconRes),
                                contentDescription = null
                            )
                        }
                    )
                }
            }
        }
    }
}