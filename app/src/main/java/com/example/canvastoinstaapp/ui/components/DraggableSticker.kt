package com.example.canvastoinstaapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.canvastoinstaapp.utils.StickerState
import kotlin.math.roundToInt

@Composable
fun DraggableSticker(
    sticker: StickerState,
    onDrag: (Float, Float) -> Unit
) {
    Image(
        painter = painterResource(id = sticker.resId),
        contentDescription = "Sticker",
        modifier = Modifier
            .offset { IntOffset(sticker.x.roundToInt(), sticker.y.roundToInt()) }
            .size(100.dp)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    onDrag(dragAmount.x, dragAmount.y)
                }
            }
    )
}