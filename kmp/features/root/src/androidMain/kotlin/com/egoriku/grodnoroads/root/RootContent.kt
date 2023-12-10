package com.egoriku.grodnoroads.root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.egoriku.grodnoroads.root.RootComponent.Child

@Composable
fun RootContent(root: RootComponent) {
    Surface {
        Children(stack = root.childStack) {
            when (it.instance) {
                Child.MainFlow -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "main"
                        )
                    }
                }
                Child.Onboarding -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "onboarding"
                        )
                    }
                }
            }
        }
    }
}