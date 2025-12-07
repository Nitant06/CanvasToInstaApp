package com.example.canvastoinstaapp.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import com.example.canvastoinstaapp.utils.DrawingLine

@Composable
fun DrawingCanvas(
    lines: List<DrawingLine>,
    onDraw: (DrawingLine) -> Unit
) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    val line = DrawingLine(
                        start = change.position - dragAmount,
                        end = change.position
                    )
                    onDraw(line)
                }
            }
    ) {
        lines.forEach { line ->
            drawLine(
                color = line.color,
                start = line.start,
                end = line.end,
                strokeWidth = line.strokeWidth,
                cap = StrokeCap.Round
            )
        }
    }
}