package com.egoriku.grodnoroads.settings.debugtools.screen.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.foundation.common.ui.SettingsTopBar
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.layout.Spacer
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import com.egoriku.grodnoroads.foundation.uikit.OutlinedSecureTextField
import com.egoriku.grodnoroads.foundation.uikit.OutlinedTextField
import com.egoriku.grodnoroads.settings.debugtools.domain.auth.AuthComponent
import com.egoriku.grodnoroads.settings.debugtools.domain.auth.AuthComponentPreview
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AuthScreen(
    authComponent: AuthComponent,
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    var showLoginDialog by rememberMutableState { false }
    val isSignedIn by authComponent.isSignedIn.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            SettingsTopBar(
                title = "Auth",
                onBack = onBack
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp)
        ) {
            AuthCard(
                isSignedIn = isSignedIn,
                onSignIn = { showLoginDialog = true },
                onSignOut = authComponent::signOut
            )
        }

        if (showLoginDialog) {
            LoginDialog(
                onSignIn = authComponent::signIn,
                onDismiss = { showLoginDialog = false }
            )
        }
    }
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun AuthScreenPreview() = GrodnoRoadsM3ThemePreview {
    AuthScreen(
        authComponent = AuthComponentPreview(),
        onBack = {}
    )
}

@Composable
private fun AuthCard(
    isSignedIn: Boolean,
    onSignIn: () -> Unit,
    onSignOut: () -> Unit
) {
    Card {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = when {
                    isSignedIn -> "Signed in"
                    else -> "Signed out"
                }
            )
            TextButton(
                onClick = when {
                    isSignedIn -> onSignOut
                    else -> onSignIn
                }
            ) {
                Text(
                    text = when {
                        isSignedIn -> "Sign out"
                        else -> "Sign in"
                    }
                )
            }
        }
    }
}

@Composable
private fun LoginDialog(
    onSignIn: suspend (email: String, password: String) -> ResultOf<Unit>,
    onDismiss: () -> Unit
) {
    var email by rememberMutableState { "" }
    val passwordState = rememberTextFieldState()
    var isLoading by rememberMutableState { false }
    var errorMessage by rememberMutableState<String?> { null }
    val coroutineScope = rememberCoroutineScope()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Sign in")
        },
        containerColor = MaterialTheme.colorScheme.surface,
        text = {
            Column {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email",
                    enabled = !isLoading,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                Spacer(8.dp)
                OutlinedSecureTextField(
                    state = passwordState,
                    label = "Password",
                    enabled = !isLoading,
                    isError = errorMessage != null,
                    supportingText = errorMessage
                )
                if (isLoading) {
                    Spacer(8.dp)
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                enabled = !isLoading && email.isNotBlank() && passwordState.text.isNotBlank(),
                onClick = {
                    isLoading = true
                    errorMessage = null
                    coroutineScope.launch {
                        when (val result = onSignIn(email, passwordState.text.toString())) {
                            is ResultOf.Success -> onDismiss()
                            is ResultOf.Failure -> {
                                isLoading = false
                                errorMessage = result.throwable.message ?: "Sign in failed"
                            }
                        }
                    }
                }
            ) {
                Text(text = "Sign in")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }
    )
}
