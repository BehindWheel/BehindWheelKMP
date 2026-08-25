package com.egoriku.grodnoroads.settings.changelog.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.changelog_empty_message
import com.egoriku.grodnoroads.compose.resources.changelog_error_message
import com.egoriku.grodnoroads.compose.resources.retry
import com.egoriku.grodnoroads.compose.resources.settings_section_changelog
import com.egoriku.grodnoroads.extensions.LoremIpsum
import com.egoriku.grodnoroads.foundation.common.ui.SettingsTopBar
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import com.egoriku.grodnoroads.foundation.theme.LocalPlatform
import com.egoriku.grodnoroads.foundation.uikit.FilterChip
import com.egoriku.grodnoroads.settings.changelog.domain.component.ChangelogComponent
import com.egoriku.grodnoroads.settings.changelog.domain.model.ChangelogEntry
import com.egoriku.grodnoroads.settings.changelog.domain.model.ChangelogPlatform
import com.egoriku.grodnoroads.settings.changelog.domain.model.ChangelogPlatform.Android
import com.egoriku.grodnoroads.settings.changelog.domain.model.ChangelogPlatform.Ios
import com.egoriku.grodnoroads.settings.changelog.domain.model.ErrorType
import com.egoriku.grodnoroads.settings.changelog.domain.store.ChangelogStore.State.Content
import com.egoriku.grodnoroads.settings.changelog.screen.mapper.toChangelogPlatform
import com.egoriku.grodnoroads.settings.changelog.screen.ui.ChangelogItem
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangelogScreen(
    changelogComponent: ChangelogComponent,
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        contentWindowInsets = WindowInsets(),
        topBar = {
            SettingsTopBar(
                scrollBehavior = scrollBehavior,
                title = stringResource(Res.string.settings_section_changelog),
                onBack = onBack
            )
        }
    ) { paddingValues ->
        val content by changelogComponent.content.collectAsState()
        val platform by changelogComponent.platform.collectAsState()

        val currentPlatform = LocalPlatform.current.toChangelogPlatform()

        LaunchedEffect(currentPlatform) {
            changelogComponent.selectPlatform(currentPlatform)
        }

        ChangelogContent(
            content = content,
            platform = platform ?: currentPlatform,
            modifier = Modifier.padding(paddingValues),
            onPlatformSelect = changelogComponent::selectPlatform,
            onRetry = { platform?.let { changelogComponent.selectPlatform(it) } }
        )
    }
}

@Composable
private fun ChangelogContent(
    content: Content,
    platform: ChangelogPlatform,
    modifier: Modifier = Modifier,
    onPlatformSelect: (ChangelogPlatform) -> Unit = {},
    onRetry: () -> Unit = {}
) {
    Column(modifier = modifier.fillMaxSize()) {
        PlatformFilterRow(
            selectedPlatform = platform,
            onPlatformSelect = onPlatformSelect
        )

        AnimatedContent(
            targetState = content,
            label = "ChangelogContent"
        ) { targetContent ->
            when (targetContent) {
                is Content.Loading -> ChangelogLoadingContent()
                is Content.Error -> ChangelogErrorContent(
                    errorType = targetContent.errorType,
                    onRetry = onRetry
                )
                is Content.Loaded -> ChangelogLoadedContent(entries = targetContent.entries)
            }
        }
    }
}

@Composable
private fun PlatformFilterRow(
    selectedPlatform: ChangelogPlatform,
    onPlatformSelect: (ChangelogPlatform) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterChip(
            selected = selectedPlatform == Android,
            onClick = { onPlatformSelect(Android) },
            label = { Text("Android") }
        )
        FilterChip(
            selected = selectedPlatform == Ios,
            onClick = { onPlatformSelect(Ios) },
            label = { Text("iOS") }
        )
    }
}

@Composable
private fun ChangelogLoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ChangelogErrorContent(
    errorType: ErrorType,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = when (errorType) {
                    ErrorType.EmptyData -> stringResource(Res.string.changelog_empty_message)
                    ErrorType.LoadFailed -> stringResource(Res.string.changelog_error_message)
                },
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Button(onClick = onRetry) {
                Text(text = stringResource(Res.string.retry))
            }
        }
    }
}

@Composable
private fun ChangelogLoadedContent(entries: List<ChangelogEntry>) {
    LazyVerticalStaggeredGrid(
        modifier = Modifier.fillMaxSize(),
        columns = StaggeredGridCells.Adaptive(250.dp),
        contentPadding = WindowInsets
            .navigationBars
            .add(
                WindowInsets(
                    left = 16.dp,
                    right = 16.dp,
                    top = 8.dp,
                    bottom = 16.dp
                )
            )
            .asPaddingValues(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalItemSpacing = 8.dp
    ) {
        itemsIndexed(entries) { index, entry ->
            ChangelogItem(
                isLatestRelease = index == 0,
                release = entry
            )
        }
    }
}

@Preview(widthDp = 800, heightDp = 400)
@PreviewGrodnoRoadsDarkLight
@Composable
private fun ChangelogContentLoadedPreview() = GrodnoRoadsM3ThemePreview {
    ChangelogContent(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        content = Content.Loaded(
            entries = listOf(
                ChangelogEntry(
                    versionName = "1.0.6",
                    notes = LoremIpsum.generateLoremIpsum(10),
                    releaseDate = "18 July, 2026"
                ),
                ChangelogEntry(
                    versionName = "1.0.5",
                    notes = LoremIpsum.generateLoremIpsum(31),
                    releaseDate = "10 June, 2026"
                ),
                ChangelogEntry(
                    versionName = "1.0.4",
                    notes = LoremIpsum.generateLoremIpsum(4),
                    releaseDate = "22 May, 2026"
                ),
                ChangelogEntry(
                    versionName = "1.0.3",
                    notes = LoremIpsum.generateLoremIpsum(31),
                    releaseDate = "15 April, 2026"
                ),
                ChangelogEntry(
                    versionName = "1.0.2",
                    notes = LoremIpsum.generateLoremIpsum(9),
                    releaseDate = "3 October, 2023"
                )
            )
        ),
        platform = Android
    )
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun ChangelogContentErrorPreview() = GrodnoRoadsM3ThemePreview {
    ChangelogContent(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        content = Content.Error(ErrorType.LoadFailed),
        platform = Ios
    )
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun ChangelogContentEmptyPreview() = GrodnoRoadsM3ThemePreview {
    ChangelogContent(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        content = Content.Error(ErrorType.EmptyData),
        platform = Android
    )
}
