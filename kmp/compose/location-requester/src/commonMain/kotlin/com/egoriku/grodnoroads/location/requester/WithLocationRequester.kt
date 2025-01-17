package com.egoriku.grodnoroads.location.requester

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun rememberLocationRequesterState(): LocationRequesterState

@Composable
expect fun WithLocationRequester(
    locationRequesterState: LocationRequesterState,
    onStateChange: (LocationRequestStatus) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
)
