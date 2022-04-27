package com.egoriku.grodnoroads.screen.chat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.egoriku.grodnoroads.R

object ChatScreenTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(R.string.tab_chat)
            val icon = rememberVectorPainter(Icons.Filled.Email)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Surface(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Чат будет чуть позже \uD83E\uDEF6",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}