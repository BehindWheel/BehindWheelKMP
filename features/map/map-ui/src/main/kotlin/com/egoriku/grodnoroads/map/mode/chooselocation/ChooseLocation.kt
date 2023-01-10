package com.egoriku.grodnoroads.map.mode.chooselocation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.map.mode.drive.action.CloseAction

@Preview
@Composable
fun ChooseLocation() {
    Box(modifier = Modifier.fillMaxSize()) {
        CloseAction(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            imageVector = Icons.Default.Close
        ) {}
    }
}