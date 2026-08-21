package com.egoriku.grodnoroads.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.resources.Res
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
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.ArrowLeft
import com.egoriku.grodnoroads.foundation.icons.outlined.ArrowRight
import com.egoriku.grodnoroads.foundation.icons.outlined.Check
import com.egoriku.grodnoroads.foundation.layout.Spacer
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryCircleButton
import com.egoriku.grodnoroads.foundation.uikit.button.SecondaryCircleButton
import com.egoriku.grodnoroads.foundation.uikit.button.common.Size
import com.egoriku.grodnoroads.onboarding.domain.component.OnboardingComponent
import com.egoriku.grodnoroads.onboarding.ui.InfographicPage
import com.egoriku.grodnoroads.onboarding.ui.PageIndicator
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun OnboardingScreen(
    onboardingComponent: OnboardingComponent,
    modifier: Modifier = Modifier
) {
    OnboardingUi(
        modifier = modifier,
        completeOnboarding = onboardingComponent::completeOnboarding
    )
}

@Composable
private fun OnboardingUi(
    modifier: Modifier = Modifier,
    completeOnboarding: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        val pagerState = rememberPagerState(pageCount = { 3 })

        HorizontalPager(
            modifier = Modifier.weight(1f),
            state = pagerState,
            userScrollEnabled = true
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
            }
        }
        Spacer(24.dp)
        Footer(
            pagerState = pagerState,
            onComplete = completeOnboarding
        )
        Spacer(24.dp)
    }
}

@Composable
private fun Footer(
    pagerState: PagerState,
    onComplete: () -> Unit
) {
    val scope = rememberCoroutineScope()

    CenterVerticallyRow(modifier = Modifier.padding(horizontal = 30.dp)) {
        PageIndicator(
            pagerState = pagerState,
            modifier = Modifier.weight(1f)
        )
        AnimatedVisibility(
            visible = pagerState.currentPage != 0,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Row {
                PreviousButton(
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }
                )
                Spacer(16.dp)
            }
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
            DoneButton(onClick = onComplete)
        }
    }
}

@Composable
private fun PreviousButton(onClick: () -> Unit) {
    SecondaryCircleButton(onClick = onClick) {
        Icon(
            imageVector = GrodnoRoads.Outlined.ArrowLeft,
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
            imageVector = GrodnoRoads.Outlined.ArrowRight,
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
            imageVector = GrodnoRoads.Outlined.Check,
            contentDescription = null
        )
    }
}
