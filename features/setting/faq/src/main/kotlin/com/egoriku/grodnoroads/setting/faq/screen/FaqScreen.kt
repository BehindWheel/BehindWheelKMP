package com.egoriku.grodnoroads.setting.faq.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.topbar.SettingsTopBar
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.setting.faq.domain.component.FaqComponent
import com.egoriku.grodnoroads.setting.faq.domain.store.FaqStore
import com.egoriku.grodnoroads.setting.faq.screen.ui.Answer
import com.egoriku.grodnoroads.setting.faq.screen.ui.Question

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FaqScreen(
    faqComponent: FaqComponent,
    onBack: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            SettingsTopBar(
                title = stringResource(id = R.string.settings_section_faq),
                onBack = onBack,
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        val state by faqComponent.state.collectAsState(initial = FaqStore.State())

        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = WindowInsets
                    .navigationBars
                    .add(WindowInsets(left = 16.dp, right = 16.dp, top = 16.dp, bottom = 16.dp))
                    .asPaddingValues(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.faq) {
                    Card(
                        onClick = {},
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
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