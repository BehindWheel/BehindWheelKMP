package com.egoriku.grodnoroads.foundation.common.ui.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
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
    onCancel: () -> Unit,
    onResult: () -> Unit = {},
): SheetCloseBehaviour {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    return remember {
        SheetCloseBehaviour(
            scope = scope,
            sheetState = sheetState,
            onCancel = onCancel,
            onResult = onResult
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
class SheetCloseBehaviour(
    private val scope: CoroutineScope,
    val sheetState: SheetState,
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
            onResult()
            sheetState.hide()
        }.invokeOnCompletion {
            if (!sheetState.isVisible) {
                onCancel()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicModalBottomSheet(
    sheetState: SheetState,
    onCancel: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    val navBarPadding = WindowInsets.navigationBars.asPaddingValues()

    ModalBottomSheet(
        onDismissRequest = onCancel,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        sheetState = sheetState,
        content = {
            Column(
                modifier = Modifier.padding(bottom = navBarPadding.calculateBottomPadding()),
                content = content
            )
        }
    )
}