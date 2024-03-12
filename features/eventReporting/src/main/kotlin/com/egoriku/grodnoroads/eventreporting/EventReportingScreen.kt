package com.egoriku.grodnoroads.eventreporting

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.eventreporting.domain.Reporting
import com.egoriku.grodnoroads.eventreporting.domain.Reporting.ReportType
import com.egoriku.grodnoroads.eventreporting.domain.ReportingOptions
import com.egoriku.grodnoroads.eventreporting.domain.model.ReportingResult
import com.egoriku.grodnoroads.eventreporting.domain.toResource
import com.egoriku.grodnoroads.eventreporting.ui.ActionBottomSheet
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.RadioButton
import com.egoriku.grodnoroads.foundation.uikit.VerticalSpacer
import com.egoriku.grodnoroads.resources.R

@Composable
fun EventReportingScreen(
    onClose: () -> Unit,
    onReport: (ReportingResult) -> Unit
) {
    var selectedType: ReportType by rememberMutableState { Reporting.roadIncidents }
    var selectedOption by rememberMutableState(selectedType) {
        selectedType.items.first()
    }
    var inputText by rememberMutableState { "" }

    ActionBottomSheet(
        onDismiss = onClose,
        onResult = {
            onReport(
                ReportingResult(
                    mapEventType = selectedOption.mapEventType,
                    shortMessage = selectedOption.toSend,
                    message = inputText
                )
            )
        }
    ) {
        ReportingUi(
            currentType = selectedType,
            selectedOption = selectedOption,
            inputText = inputText,
            onTypeChanged = {
                selectedType = it
            },
            onSelectedOptionChanged = {
                selectedOption = it
            },
            onInputChanged = {
                inputText = it
            }
        )
    }
}

@Composable
fun ReportingUi(
    currentType: ReportType,
    selectedOption: ReportingOptions,
    inputText: String,
    onTypeChanged: (ReportType) -> Unit,
    onSelectedOptionChanged: (ReportingOptions) -> Unit,
    onInputChanged: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .verticalScroll(rememberScrollState())
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            text = stringResource(R.string.reporting_header),
            style = MaterialTheme.typography.headlineSmall
        )
        VerticalSpacer(16.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            CategoryCell(
                modifier = Modifier.weight(1f),
                name = stringResource(R.string.reporting_category_road_incidents),
                iconRes = R.drawable.ic_reporting_road_problem,
                selected = currentType == Reporting.roadIncidents,
                onClick = {
                    onTypeChanged(Reporting.roadIncidents)
                    focusManager.clearFocus()
                }
            )
            CategoryCell(
                modifier = Modifier.weight(1f),
                name = stringResource(R.string.reporting_category_traffic_police),
                iconRes = R.drawable.ic_reporting_traffic_police,
                selected = currentType == Reporting.trafficPolice,
                onClick = {
                    onTypeChanged(Reporting.trafficPolice)
                    focusManager.clearFocus()
                }
            )
            CategoryCell(
                modifier = Modifier.weight(1f),
                name = stringResource(R.string.reporting_category_other),
                iconRes = R.drawable.ic_reporting_other,
                selected = currentType == Reporting.other,
                onClick = {
                    onTypeChanged(Reporting.other)
                    focusManager.clearFocus()
                }
            )
        }
        VerticalSpacer(24.dp)
        currentType.items.forEach { entry ->
            RadioButtonListItem(
                text = stringResource(entry.toResource()),
                selected = selectedOption == entry,
                onSelect = {
                    onSelectedOptionChanged(entry)
                    focusManager.clearFocus()
                }
            )
        }
        VerticalSpacer(16.dp)
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            ),
            value = inputText,
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            onValueChange = onInputChanged,
            label = { Text(stringResource(R.string.dialog_input_hint)) }
        )
        VerticalSpacer(24.dp)
    }
}

@Composable
private fun CategoryCell(
    name: String,
    @DrawableRes iconRes: Int,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val selectedModifier = if (selected) {
        Modifier.border(
            width = 2.dp,
            color = MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(10.dp)
        )
    } else {
        Modifier
    }
    Column(
        modifier = modifier
            .heightIn(min = 132.dp)
            .then(selectedModifier)
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(iconRes),
            contentDescription = null
        )
        Text(
            text = name,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun RadioButtonListItem(
    text: String,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .toggleable(
                value = selected,
                role = Role.RadioButton,
                onValueChange = { onSelect() }
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelect,
        )
        Text(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .weight(1f),
            text = text,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@GrodnoRoadsPreview
@Composable
private fun ReportingUiPreview() = GrodnoRoadsM3ThemePreview {
    var selectedType by rememberMutableState { Reporting.roadIncidents }
    var selectedOption by rememberMutableState(selectedType) {
        selectedType.items.first()
    }
    var inputText by remember { mutableStateOf("") }

    ReportingUi(
        currentType = selectedType,
        selectedOption = selectedOption,
        inputText = inputText,
        onTypeChanged = {
            selectedType = it
        },
        onSelectedOptionChanged = {
            selectedOption = it
        },
        onInputChanged = {
            inputText = it
        }
    )
}