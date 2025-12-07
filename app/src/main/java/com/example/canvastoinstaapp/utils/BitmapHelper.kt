package com.example.canvastoinstaapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.net.Uri
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import androidx.core.graphics.scale

// --- Data Models ---
data class DrawingLine(
    val start: Offset,
    val end: Offset,
    val color: Color = Color.Red,
    val strokeWidth: Float = 10f
)

data class StickerState(
    val id: Long = System.currentTimeMillis(), // Unique ID
    val resId: Int,
    var x: Float,
    var y: Float
)

// --- Helper Object ---
object BitmapHelper {
    fun saveEditedImage(
        context: Context,
        imagePath: String,
        lines: List<DrawingLine>,
        stickers: List<StickerState>,
        caption: String
    ): Uri? {
        try {
            val originalBitmap = BitmapFactory.decodeFile(imagePath)
                .copy(Bitmap.Config.ARGB_8888, true)

            val canvas = Canvas(originalBitmap)
            val paint = Paint().apply {
                style = Paint.Style.STROKE
                strokeJoin = Paint.Join.ROUND
                strokeCap = Paint.Cap.ROUND
                isAntiAlias = true
            }

            // Draw Lines
            lines.forEach { line ->
                paint.color = line.color.toArgb()
                paint.strokeWidth = line.strokeWidth
                canvas.drawLine(line.start.x, line.start.y, line.end.x, line.end.y, paint)
            }

            // Draw Stickers
            stickers.forEach { sticker ->
                val stickerBitmap = BitmapFactory.decodeResource(context.resources, sticker.resId)
                val scaledSticker = stickerBitmap.scale(250, 250)
                canvas.drawBitmap(scaledSticker, sticker.x, sticker.y, null)
            }

            // Draw Caption
            if (caption.isNotEmpty()) {
                val textPaint = Paint().apply {
                    color = android.graphics.Color.WHITE
                    textSize = 120f
                    style = Paint.Style.FILL
                    setShadowLayer(10f, 0f, 0f, android.graphics.Color.BLACK)
                }
                canvas.drawText(caption, 100f, originalBitmap.height - 300f, textPaint)
            }

            // Save File
            val file = File(context.cacheDir, "story_export_${System.currentTimeMillis()}.jpg")
            val out = FileOutputStream(file)
            originalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()

            return FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                file
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}