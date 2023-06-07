package com.egoriku.grodnoroads.foundation.uikit.bottomsheet

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberSheetCloseBehaviour(
    sheetState: () -> SheetState,
    onCancel: () -> Unit,
    onResult: () -> Unit = {},
): SheetCloseBehaviour {
    val scope = rememberCoroutineScope()
    return remember {
        SheetCloseBehaviour(
            scope = scope,
            sheetState = sheetState(),
            onCancel = onCancel,
            onResult = onResult
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
class SheetCloseBehaviour(
    private val scope: CoroutineScope,
    private val sheetState: SheetState,
    private val onCancel: () -> Unit,
    private val onResult: () -> Unit,
) {
    fun cancel() {
        scope.launch {
            sheetState.hide()
        }.invokeOnCompletion {
            if (!sheetState.isVisible) {
                onCancel()
            }
        }
    }

    fun hideWithResult() {
        scope.launch {
            sheetState.hide()
        }.invokeOnCompletion {
            if (!sheetState.isVisible) {
                onResult()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicModalBottomSheet(
    sheetState: SheetState,
    onCancel: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
    footer: @Composable ColumnScope.() -> Unit,
    footerPadding: PaddingValues = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
) {
    val navBarPadding = WindowInsets.navigationBars.asPaddingValues()

    ModalBottomSheet(
        onDismissRequest = onCancel,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        sheetState = sheetState,
        content = {
            Column(
                modifier = Modifier.weight(1f, fill = false),
                content = content
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(footerPadding)
                    .padding(bottom = navBarPadding.calculateBottomPadding()),
                content = footer
            )
        }
    )
}