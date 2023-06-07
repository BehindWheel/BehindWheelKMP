package com.egoriku.grodnoroads.foundation.uikit.bottomsheet

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryButton
import com.egoriku.grodnoroads.foundation.uikit.button.TextButton
import com.egoriku.grodnoroads.resources.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicModalBottomSheet(
    onDismiss: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
    footer: @Composable ColumnScope.() -> Unit
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val navBarPadding = WindowInsets.navigationBars.asPaddingValues()

    val dismiss = remember {
        {
            scope.launch {
                sheetState.hide()
            }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    onDismiss()
                }
            }
            Unit
        }
    }

    ModalBottomSheet(
        onDismissRequest = dismiss,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        state = sheetState,
        content = {
            Column(
                modifier = Modifier
                    .weight(1f, fill = false)
                    .padding(horizontal = 16.dp),
                content = content
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(bottom = navBarPadding.calculateBottomPadding()),
                content = footer
            )
        }
    )
}

@Composable
private fun Footer(paddingValues: PaddingValues, onDismiss: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .padding(bottom = paddingValues.calculateBottomPadding()),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextButton(modifier = Modifier.weight(1f), onClick = onDismiss) {
            Text(stringResource(id = R.string.cancel))
        }
        PrimaryButton(modifier = Modifier.weight(1f), onClick = onDismiss) {
            Text(stringResource(id = R.string.ok))
        }
    }
}