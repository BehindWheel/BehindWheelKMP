package com.egoriku.grodnoroads.foundation.dialog

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.egoriku.grodnoroads.extensions.logD
import com.egoriku.grodnoroads.foundation.dialog.content.DialogButton
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsTheme
import com.egoriku.grodnoroads.resources.R
import kotlin.math.roundToInt

@Composable
fun StepperDialog(
    initial: Float,
    min: Float,
    max: Float,
    step: Float,
    onClose: () -> Unit,
    onSelected: (Float) -> Unit
) {
    var sliderPosition by remember { mutableStateOf(initial) }

    val drawPadding = with(LocalDensity.current) { 10.dp.toPx() }
    val textSize = with(LocalDensity.current) { 10.dp.toPx() }
    val color = MaterialTheme.colors.onSurface.toArgb()

    val textPaint by remember {
        mutableStateOf(
            Paint().apply {
                textAlign = Paint.Align.CENTER
                this.color = color
                this.textSize = textSize
            }
        )
    }

    val steps = (((max - min) / step) - 1f).roundToInt()

    val valuesRange = buildList {
        var i = min
        while (i <= max) {
            add(i.toString())
            i += step
        }
    }

    Dialog(onDismissRequest = onClose) {
        DialogContent {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 32.dp)) {
                Slider(
                    value = sliderPosition,
                    steps = steps,
                    valueRange = min..max,
                    onValueChange = {
                        sliderPosition = it
                        logD(sliderPosition.toString())
                    }
                )

                Canvas(modifier = Modifier.fillMaxWidth()) {
                    val distance = (size.width - 2 * drawPadding) / (valuesRange.size - 1)

                    valuesRange.forEachIndexed { index, date ->
                        drawContext.canvas.nativeCanvas.drawText(
                            date,
                            drawPadding + index.times(distance),
                            size.height,
                            textPaint
                        )
                    }
                }
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                DialogButton(
                    modifier = Modifier.weight(1f),
                    textResId = R.string.cancel,
                    onClick = onClose
                )
                DialogButton(
                    modifier = Modifier.weight(1f),
                    textResId = R.string.ok,
                    onClick = {
                        onSelected(sliderPosition)
                    }
                )
            }
        }
    }
}

@GrodnoRoadsPreview
@Composable
private fun PreviewStepperDialog() {
    GrodnoRoadsTheme {
        Column {
            StepperDialog(
                initial = 7f,
                min = 6f,
                max = 7f,
                step = 0.5f,
                onClose = {}
            ) {}
        }
    }
}

@GrodnoRoadsPreview
@Composable
private fun PreviewStepperDialog2() {
    GrodnoRoadsTheme {
        Column {
            StepperDialog(
                initial = 10f,
                min = 10f,
                max = 13f,
                step = 0.5f,
                onClose = {}
            ) {}
        }
    }
}

@GrodnoRoadsPreview
@Composable
private fun PreviewStepperDialog3() {
    GrodnoRoadsTheme {
        Column {
            StepperDialog(
                initial = 7f,
                min = 5f,
                max = 7f,
                step = 0.5f,
                onClose = {}
            ) {}
        }
    }
}