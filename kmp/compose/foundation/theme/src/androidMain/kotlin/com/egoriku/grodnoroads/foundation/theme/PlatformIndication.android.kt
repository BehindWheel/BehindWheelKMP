package com.egoriku.grodnoroads.foundation.theme

import androidx.compose.foundation.Indication
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable

@Composable
actual fun platformIndication(): Indication = ripple()

@Composable
actual fun platformRippleConfiguration(): RippleConfiguration? = LocalRippleConfiguration.current
