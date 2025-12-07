package com.example.canvastoinstaapp.ui.editor


import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.canvastoinstaapp.utils.BitmapHelper
import com.example.canvastoinstaapp.utils.DrawingLine
import com.example.canvastoinstaapp.utils.InstagramHelper
import com.example.canvastoinstaapp.utils.StickerState

class EditorViewModel : ViewModel() {

    // State
    val lines = mutableStateListOf<DrawingLine>()
    val stickers = mutableStateListOf<StickerState>()
    var caption = mutableStateOf("")

    fun addLine(line: DrawingLine) {
        lines.add(line)
    }

    fun addSticker(resId: Int) {
        stickers.add(StickerState(resId = resId, x = 200f, y = 200f))
    }

    fun updateStickerPosition(index: Int, dx: Float, dy: Float) {
        val current = stickers[index]
        stickers[index] = current.copy(
            x = current.x + dx,
            y = current.y + dy
        )
    }

    fun updateCaption(text: String) {
        caption.value = text
    }

    fun clearAll() {
        lines.clear()
        stickers.clear()
        caption.value = ""
    }

    fun saveAndShare(context: Context, imagePath: String) {
        val uri = BitmapHelper.saveEditedImage(
            context,
            imagePath,
            lines,
            stickers,
            caption.value
        )
        if (uri != null) {
            InstagramHelper.shareToStory(context, uri)
        }
    }
}