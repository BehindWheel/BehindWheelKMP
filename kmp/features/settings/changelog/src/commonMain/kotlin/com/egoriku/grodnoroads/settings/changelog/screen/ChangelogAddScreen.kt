package com.egoriku.grodnoroads.settings.changelog.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.snackbar.SnackbarHost
import com.egoriku.grodnoroads.compose.snackbar.model.MessageData.Raw
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarMessage.SimpleMessage
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarState
import com.egoriku.grodnoroads.foundation.common.ui.SettingsTopBar
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.layout.WeightSpacer
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import com.egoriku.grodnoroads.foundation.uikit.OutlinedTextField
import com.egoriku.grodnoroads.settings.changelog.domain.component.ChangelogAddComponent
import com.egoriku.grodnoroads.settings.changelog.domain.model.ChangelogPlatform
import com.egoriku.grodnoroads.settings.changelog.domain.model.ChangelogPlatform.Android
import com.egoriku.grodnoroads.settings.changelog.domain.model.ChangelogPlatform.Ios
import com.egoriku.grodnoroads.shared.formatter.ChangelogFormatter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

private val bulletEmojis = listOf("🔸", "🐞")

private fun continueBulletList(previous: TextFieldValue, current: TextFieldValue): TextFieldValue {
    val insertedNewline = current.text.length == previous.text.length + 1 &&
        current.selection.collapsed &&
        current.selection.start > 0 &&
        current.text[current.selection.start - 1] == '\n'

    if (!insertedNewline) {
        return current
    }

    val cursor = current.selection.start
    val previousLineStart = current.text.lastIndexOf('\n', cursor - 2) + 1
    val previousLine = current.text.substring(previousLineStart, cursor - 1)

    val bullet = bulletEmojis.firstOrNull { previousLine.trimStart().startsWith(it) } ?: return current
    val contentAfterBullet = previousLine.substringAfter(bullet).trim()
    if (contentAfterBullet.isEmpty()) {
        return current
    }

    val insertText = "$bullet "
    val newText = current.text.substring(0, cursor) + insertText + current.text.substring(cursor)
    return TextFieldValue(
        text = newText,
        selection = TextRange(cursor + insertText.length)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangelogAddScreen(
    changelogAddComponent: ChangelogAddComponent,
    modifier: Modifier = Modifier
) {
    val state by changelogAddComponent.state.collectAsState()

    val snackbarState = remember { SnackbarState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(state.saveError) {
        state.saveError?.let {
            scope.launch {
                snackbarState.show(
                    SimpleMessage(title = Raw(it.message ?: "Failed to save changelog entry"))
                )
            }
        }
    }

    var showDatePicker by rememberMutableState { false }
    var notesFieldValue by rememberMutableState { TextFieldValue(state.notes) }

    Scaffold(
        modifier = modifier,
        topBar = {
            SettingsTopBar(
                title = if (state.isEditing) "Edit version" else "Add version",
                onBack = changelogAddComponent::onBack
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                PlatformSegmentedRow(
                    platform = state.platform,
                    onPlatformSelect = changelogAddComponent::setPlatform
                )
                OutlinedTextField(
                    value = state.versionName,
                    onValueChange = changelogAddComponent::setVersionName,
                    label = "Version name"
                )
                OutlinedTextField(
                    modifier = Modifier.height(300.dp),
                    value = notesFieldValue,
                    onValueChange = { newValue ->
                        notesFieldValue = continueBulletList(notesFieldValue, newValue)
                        changelogAddComponent.setNotes(notesFieldValue.text)
                    },
                    label = "Notes",
                    singleLine = false
                )
                TextButton(onClick = { showDatePicker = true }) {
                    Text(text = ChangelogFormatter.format(state.releaseDateMillis))
                }
                WeightSpacer()
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isSaving && state.versionName.isNotBlank() && state.notes.isNotBlank(),
                    onClick = changelogAddComponent::save
                ) {
                    Text(text = "Save")
                }
            }
            SnackbarHost(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(paddingValues),
                hostState = snackbarState,
                paddingValues = PaddingValues(16.dp)
            )
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(initialSelectedDateMillis = state.releaseDateMillis)

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            changelogAddComponent.setReleaseDateMillis(it)
                        }
                        showDatePicker = false
                    }
                ) {
                    Text(text = "OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(text = "Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
private fun PlatformSegmentedRow(
    platform: ChangelogPlatform,
    onPlatformSelect: (ChangelogPlatform) -> Unit
) {
    SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
        SegmentedButton(
            shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
            onClick = { onPlatformSelect(Android) },
            selected = platform == Android,
            label = { Text("Android") }
        )
        SegmentedButton(
            shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
            onClick = { onPlatformSelect(Ios) },
            selected = platform == Ios,
            label = { Text("iOS") }
        )
    }
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun ChangelogAddScreenPreview() = GrodnoRoadsM3ThemePreview {
    ChangelogAddScreen(changelogAddComponent = FakeChangelogAddComponent())
}

private class FakeChangelogAddComponent : ChangelogAddComponent {
    override val state = MutableStateFlow(
        ChangelogAddComponent.State(
            isEditing = false,
            platform = Android,
            versionName = "1.7.0",
            notes = "Added new feature",
            releaseDateMillis = 0L,
            isSaving = false,
            saveError = null
        )
    )

    override fun setPlatform(platform: ChangelogPlatform) = Unit
    override fun setVersionName(versionName: String) = Unit
    override fun setNotes(notes: String) = Unit
    override fun setReleaseDateMillis(millis: Long) = Unit
    override fun save() = Unit
    override fun onBack() = Unit
}
