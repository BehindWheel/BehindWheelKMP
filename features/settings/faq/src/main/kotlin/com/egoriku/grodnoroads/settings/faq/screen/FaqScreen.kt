package com.egoriku.grodnoroads.settings.faq.screen

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.bottombar.BottomBarVisibility
import com.egoriku.grodnoroads.foundation.bottombar.BottomBarVisibilityState.HIDDEN
import com.egoriku.grodnoroads.foundation.topbar.SettingsTopBar
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.settings.faq.domain.component.FaqComponent
import com.egoriku.grodnoroads.settings.faq.domain.store.FaqStore
import com.egoriku.grodnoroads.settings.faq.screen.ui.Answer
import com.egoriku.grodnoroads.settings.faq.screen.ui.Question

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun FaqScreen(
    faqComponent: FaqComponent,
    onBack: () -> Unit
) {
    BottomBarVisibility(HIDDEN)

    Scaffold(
        topBar = {
            SettingsTopBar(
                title = stringResource(id = R.string.settings_section_faq),
                onBack = onBack
            )
        }
    ) {
        val state by faqComponent.state.collectAsState(initial = FaqStore.State())

        AnimatedContent(
            targetState = state.isLoading,
            transitionSpec = { fadeIn() with fadeOut() })
        { isLoading ->
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.faq) {
                        Card(
                            onClick = {},
                            elevation = 3.dp,
                            shape = RoundedCornerShape(10.dp),
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(all = 16.dp)
                            ) {
                                Question(text = it.question)
                                Answer(text = it.answer)
                            }
                        }
                    }
                }
            }
        }
    }
}