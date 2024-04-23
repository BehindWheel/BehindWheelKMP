package com.egoriku.grodnoroads.foundation.uikit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview

@Composable
fun OutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    supportingText: String? = null,
    onFocusChange: () -> Unit
) {
    var isFirstFocus by rememberMutableState { true }

    OutlinedTextField(
        modifier = modifier
            .onFocusChanged { focusState ->
                when {
                    isFirstFocus -> isFirstFocus = false
                    !focusState.isFocused -> onFocusChange()
                }
            },
        value = value,
        onValueChange = onValueChange,
        label = label,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        supportingText = supportingText,
    )
}

@Composable
fun OutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    supportingText: String? = null
) {
    androidx.compose.material3.OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        value = value,
        shape = RoundedCornerShape(16.dp),
        singleLine = true,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        supportingText = when {
            supportingText != null -> {
                {
                    Text(
                        text = supportingText,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
            else -> null
        }
    )
}

@GrodnoRoadsPreview
@Composable
private fun OutlinedTextFieldPreview() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = "Text",
            onValueChange = {},
            label = "Label",
        )
        OutlinedTextField(
            value = "Text",
            onValueChange = {},
            label = "Label",
            supportingText = "Error text"
        )
    }
}