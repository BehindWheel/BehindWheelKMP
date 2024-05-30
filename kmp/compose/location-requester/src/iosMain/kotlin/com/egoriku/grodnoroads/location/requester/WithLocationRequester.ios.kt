package com.egoriku.grodnoroads.location.requester

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
actual fun rememberLocationRequesterState() = remember { LocationRequesterState() }

@Composable
actual fun WithLocationRequester(
    locationRequesterState: LocationRequesterState,
    modifier: Modifier,
    onStateChanged: (LocationRequestStatus) -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    // TODO: Implement ios part
}