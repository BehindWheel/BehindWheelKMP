package com.egoriku.grodnoroads.foundation.uikit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.shared.resources.MR
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun OutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
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
        isError = isError,
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
    enabled: Boolean = true,
    isError: Boolean = false,
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
        enabled = enabled,
        isError = isError,
        colors = OutlinedTextFieldDefaults.colors().copy(
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
            disabledIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),

            focusedLabelColor = MaterialTheme.colorScheme.onSurface,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.45f),
            disabledLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),

            errorLabelColor = MaterialTheme.colorScheme.error,

            errorSupportingTextColor = MaterialTheme.colorScheme.error,

            disabledTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
        ),
        onValueChange = onValueChange,
        label = { Text(text = label) },
        trailingIcon = when {
            isError -> {
                {
                    Icon(
                        painter = painterResource(MR.images.ic_error),
                        contentDescription = null
                    )
                }
            }
            else -> null
        },
        supportingText = when {
            supportingText != null -> {
                {
                    Text(text = supportingText)
                }
            }
            else -> null
        }
    )
}

@GrodnoRoadsPreview
@Composable
private fun OutlinedTextFieldPreview() = GrodnoRoadsM3ThemePreview {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = "Опциональное сообщение",
        )
        OutlinedTextField(
            value = "Тавлая",
            onValueChange = {},
            isError = true,
            label = "Обязательное сообщение*",
            supportingText = "Текст описания ошибки"
        )
        OutlinedTextField(
            value = "Тавлая",
            onValueChange = {},
            label = "Опциональное сообщение",
        )

        OutlinedTextField(
            value = "Тавлая",
            enabled = false,
            onValueChange = {},
            label = "Опциональное сообщение",
        )
    }
}