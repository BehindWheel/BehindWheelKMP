package com.egoriku.grodnoroads.foundation.uikit.bottomsheet

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import com.egoriku.grodnoroads.foundation.theme.tonalElevation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBottomSheet(
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    shape: Shape = BottomSheetDefaults.ExpandedShape,
    dragHandle: @Composable (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    ModalBottomSheet(
        tonalElevation = MaterialTheme.tonalElevation,
        onDismissRequest = onDismissRequest,
        shape = shape,
        dragHandle = dragHandle,
        sheetState = sheetState,
        windowInsets = WindowInsets(0, 0, 0, 0),
        content = content
    )
}