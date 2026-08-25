package com.egoriku.grodnoroads.foundation.uikit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedSecureTextField as Material3OutlinedSecureTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.Error
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoads

@Composable
fun OutlinedSecureTextField(
    state: TextFieldState,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    supportingText: String? = null
) {
    Material3OutlinedSecureTextField(
        state = state,
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = keyboardOptions,
        shape = RoundedCornerShape(16.dp),
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

            disabledTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        ),
        label = { Text(text = label) },
        trailingIcon = when {
            isError -> {
                {
                    Icon(
                        imageVector = GrodnoRoads.Outlined.Error,
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

@PreviewGrodnoRoads
@Composable
private fun OutlinedSecureTextFieldPreview() = GrodnoRoadsM3ThemePreview {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedSecureTextField(
            state = rememberTextFieldState(),
            label = "Password"
        )
        OutlinedSecureTextField(
            state = rememberTextFieldState("Password"),
            isError = true,
            label = "Password*",
            supportingText = "Error description text"
        )
        OutlinedSecureTextField(
            state = rememberTextFieldState("Password"),
            enabled = false,
            label = "Password"
        )
    }
}
