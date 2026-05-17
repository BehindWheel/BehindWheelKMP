package com.egoriku.grodnoroads.foundation.theme

import androidx.compose.foundation.Indication
import androidx.compose.material3.RippleConfiguration
import androidx.compose.runtime.Composable

@Composable
expect fun platformIndication(): Indication

@Composable
expect fun platformRippleConfiguration(): RippleConfiguration?
