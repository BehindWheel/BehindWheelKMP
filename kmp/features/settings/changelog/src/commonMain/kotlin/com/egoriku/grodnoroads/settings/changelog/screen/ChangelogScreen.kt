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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.egoriku.grodnoroads.compose.snackbar.SnackbarHost
import com.egoriku.grodnoroads.compose.snackbar.model.MessageData.Raw
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarMessage.SimpleMessage
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarState
import com.egoriku.grodnoroads.extensions.LoremIpsum
import com.egoriku.grodnoroads.foundation.common.ui.SettingsTopBar
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.Add
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
    onAddClick: () -> Unit = {},
    onEditClick: (ChangelogEntry) -> Unit = {},
    onBack: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val allowedModify by changelogComponent.allowedModify.collectAsState()
    val content by changelogComponent.content.collectAsState()
    val platform by changelogComponent.platform.collectAsState()
    val currentPlatform = LocalPlatform.current.toChangelogPlatform()
    val resolvedPlatform = platform ?: currentPlatform

    val snackbarState = remember { SnackbarState() }
    var entryToDelete by rememberMutableState<ChangelogEntry?> { null }

    LaunchedEffect(currentPlatform) {
        changelogComponent.selectPlatform(currentPlatform)
    }

    LaunchedEffect(Unit) {
        changelogComponent.labels.collect { label ->
            when (label) {
                ChangelogComponent.Label.DeleteFailed -> snackbarState.show(
                    SimpleMessage(title = Raw("Failed to delete entry"))
                )
            }
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        contentWindowInsets = WindowInsets(),
        topBar = {
            SettingsTopBar(
                scrollBehavior = scrollBehavior,
                title = stringResource(Res.string.settings_section_changelog),
                onBack = onBack
            )
        },
        floatingActionButton = {
            if (allowedModify) {
                FloatingActionButton(
                    modifier = Modifier.navigationBarsPadding(),
                    onClick = onAddClick
                ) {
                    Icon(
                        imageVector = GrodnoRoads.Outlined.Add,
                        contentDescription = null
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            ChangelogContent(
                content = content,
                platform = resolvedPlatform,
                allowedModify = allowedModify,
                modifier = Modifier.padding(paddingValues),
                onPlatformSelect = changelogComponent::selectPlatform,
                onRetry = { changelogComponent.selectPlatform(resolvedPlatform) },
                onEditClick = onEditClick,
                onDeleteClick = { entryToDelete = it }
            )

            SnackbarHost(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(paddingValues),
                hostState = snackbarState
            )
        }
    }

    entryToDelete?.let { entry ->
        DeleteChangelogEntryDialog(
            versionName = entry.versionName,
            onConfirm = {
                changelogComponent.deleteEntry(entry.id)
                entryToDelete = null
            },
            onDismiss = { entryToDelete = null }
        )
    }
}

@Composable
private fun DeleteChangelogEntryDialog(
    versionName: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Delete version") },
        text = { Text(text = "Are you sure you want to delete $versionName?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = "Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }
    )
}

@Composable
private fun ChangelogContent(
    content: Content,
    platform: ChangelogPlatform,
    modifier: Modifier = Modifier,
    allowedModify: Boolean = false,
    onPlatformSelect: (ChangelogPlatform) -> Unit = {},
    onRetry: () -> Unit = {},
    onEditClick: (ChangelogEntry) -> Unit = {},
    onDeleteClick: (ChangelogEntry) -> Unit = {}
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
                is Content.Loaded -> ChangelogLoadedContent(
                    entries = targetContent.entries,
                    allowedModify = allowedModify,
                    onEditClick = onEditClick,
                    onDeleteClick = onDeleteClick
                )
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
private fun ChangelogLoadedContent(
    entries: List<ChangelogEntry>,
    allowedModify: Boolean = false,
    onEditClick: (ChangelogEntry) -> Unit = {},
    onDeleteClick: (ChangelogEntry) -> Unit = {}
) {
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
                release = entry,
                allowedModify = allowedModify,
                onEditClick = { onEditClick(entry) },
                onDeleteClick = { onDeleteClick(entry) }
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
                    id = "1",
                    platform = Android,
                    versionName = "1.0.6",
                    notes = LoremIpsum.generateLoremIpsum(10),
                    releaseDateMillis = 1784448000000L
                ),
                ChangelogEntry(
                    id = "2",
                    platform = Android,
                    versionName = "1.0.5",
                    notes = LoremIpsum.generateLoremIpsum(31),
                    releaseDateMillis = 1781136000000L
                ),
                ChangelogEntry(
                    id = "3",
                    platform = Android,
                    versionName = "1.0.4",
                    notes = LoremIpsum.generateLoremIpsum(4),
                    releaseDateMillis = 1779408000000L
                ),
                ChangelogEntry(
                    id = "4",
                    platform = Android,
                    versionName = "1.0.3",
                    notes = LoremIpsum.generateLoremIpsum(31),
                    releaseDateMillis = 1776211200000L
                ),
                ChangelogEntry(
                    id = "5",
                    platform = Android,
                    versionName = "1.0.2",
                    notes = LoremIpsum.generateLoremIpsum(9),
                    releaseDateMillis = 1696291200000L
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
