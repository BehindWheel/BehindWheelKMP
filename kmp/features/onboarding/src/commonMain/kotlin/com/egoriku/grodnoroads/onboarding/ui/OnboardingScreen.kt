package com.egoriku.grodnoroads.onboarding.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.ic_arrow_left
import com.egoriku.grodnoroads.compose.resources.ic_arrow_right
import com.egoriku.grodnoroads.compose.resources.ic_check
import com.egoriku.grodnoroads.compose.resources.img_1
import com.egoriku.grodnoroads.compose.resources.img_2
import com.egoriku.grodnoroads.compose.resources.img_3
import com.egoriku.grodnoroads.compose.resources.onboarding_infographic_1_description
import com.egoriku.grodnoroads.compose.resources.onboarding_infographic_1_title
import com.egoriku.grodnoroads.compose.resources.onboarding_infographic_2_description
import com.egoriku.grodnoroads.compose.resources.onboarding_infographic_2_title
import com.egoriku.grodnoroads.compose.resources.onboarding_infographic_3_description
import com.egoriku.grodnoroads.compose.resources.onboarding_infographic_3_title
import com.egoriku.grodnoroads.foundation.core.CenterVerticallyRow
import com.egoriku.grodnoroads.foundation.uikit.HorizontalSpacer
import com.egoriku.grodnoroads.foundation.uikit.VerticalSpacer
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryCircleButton
import com.egoriku.grodnoroads.foundation.uikit.button.SecondaryCircleButton
import com.egoriku.grodnoroads.foundation.uikit.button.common.Size
import com.egoriku.grodnoroads.onboarding.domain.component.OnboardingComponent
import com.egoriku.grodnoroads.onboarding.domain.component.OnboardingComponent.OnboardingPref
import com.egoriku.grodnoroads.onboarding.domain.store.OnboardingStore.State
import com.egoriku.grodnoroads.onboarding.ui.foundation.ChooseCityPage
import com.egoriku.grodnoroads.onboarding.ui.foundation.InfographicPage
import com.egoriku.grodnoroads.onboarding.ui.foundation.PageIndicator
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun OnboardingScreen(onboardingComponent: OnboardingComponent) {
    val state by onboardingComponent.state.collectAsState(State())

    Box(modifier = Modifier.systemBarsPadding()) {
        OnboardingUi(
            state = state,
            onModify = onboardingComponent::modify,
            onFinish = onboardingComponent::completeOnboarding
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun OnboardingUi(
    state: State,
    onModify: (OnboardingPref) -> Unit,
    onFinish: () -> Unit
) {
    Column {
        val pagerState = rememberPagerState(pageCount = { 4 })

        HorizontalPager(
            modifier = Modifier.weight(1f),
            state = pagerState,
            userScrollEnabled = true,
        ) {
            when (it) {
                0 -> InfographicPage(
                    resource = Res.drawable.img_1,
                    title = stringResource(Res.string.onboarding_infographic_1_title),
                    description = stringResource(Res.string.onboarding_infographic_1_description)
                )
                1 -> InfographicPage(
                    resource = Res.drawable.img_2,
                    title = stringResource(Res.string.onboarding_infographic_2_title),
                    description = stringResource(Res.string.onboarding_infographic_2_description)
                )
                2 -> InfographicPage(
                    resource = Res.drawable.img_3,
                    title = stringResource(Res.string.onboarding_infographic_3_title),
                    description = stringResource(Res.string.onboarding_infographic_3_description)
                )
                3 -> ChooseCityPage(
                    defaultCity = state.defaultCity,
                    onModify = onModify
                )
            }
        }
        VerticalSpacer(24.dp)
        Footer(
            pagerState = pagerState,
            onFinish = onFinish
        )
        VerticalSpacer(24.dp)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Footer(
    pagerState: PagerState,
    onFinish: () -> Unit
) {
    val scope = rememberCoroutineScope()

    CenterVerticallyRow(modifier = Modifier.padding(horizontal = 30.dp)) {
        PageIndicator(
            pagerState = pagerState,
            modifier = Modifier.weight(1f)
        )

        if (pagerState.currentPage != 0) {
            PreviousButton(
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                }
            )
            HorizontalSpacer(16.dp)
        }

        if (pagerState.currentPage != pagerState.pageCount - 1) {
            NextButton(
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            )
        } else {
            DoneButton(onClick = onFinish)
        }
    }
}

@Composable
private fun PreviousButton(onClick: () -> Unit) {
    SecondaryCircleButton(onClick = onClick) {
        Icon(
            painter = painterResource(Res.drawable.ic_arrow_left),
            contentDescription = null
        )
    }
}

@Composable
private fun NextButton(onClick: () -> Unit) {
    PrimaryCircleButton(
        onClick = onClick,
        size = Size.Small
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_arrow_right),
            contentDescription = null
        )
    }
}

@Composable
private fun DoneButton(onClick: () -> Unit) {
    PrimaryCircleButton(
        onClick = onClick,
        size = Size.Small
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_check),
            contentDescription = null
        )
    }
}