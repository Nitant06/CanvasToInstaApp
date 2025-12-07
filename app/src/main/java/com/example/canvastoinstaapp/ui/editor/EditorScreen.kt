package com.example.canvastoinstaapp.ui.editor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.canvastoinstaapp.R
import com.example.canvastoinstaapp.ui.components.DraggableSticker
import com.example.canvastoinstaapp.ui.components.DrawingCanvas
import java.io.File

@Composable
fun EditorScreen(
    imagePath: String,
    viewModel: EditorViewModel = viewModel(),
    onBack: () -> Unit
) {
    val context = LocalContext.current

    val availableStickers = listOf(
        R.drawable.sticker_wow,
        R.drawable.sticker_idea,
        R.drawable.sticker_alert
    )

    Box(modifier = Modifier.fillMaxSize()) {

        // 1. Background Image
        Image(
            painter = rememberAsyncImagePainter(File(imagePath)),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // 2. Drawing Canvas
        DrawingCanvas(
            lines = viewModel.lines,
            onDraw = { line -> viewModel.addLine(line) }
        )

        // 3. Stickers
        viewModel.stickers.forEachIndexed { index, sticker ->
            DraggableSticker(
                sticker = sticker,
                onDrag = { dx, dy ->
                    viewModel.updateStickerPosition(index, dx, dy)
                }
            )
        }

        // 4. Top Overlay (Add Stickers / Clear)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onBack() }) {
                Icon(Icons.Default.Close, contentDescription = "Discard", tint = Color.White)
            }

            // Sticker Picker
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                availableStickers.forEach { resId ->
                    Image(
                        painter = painterResource(id = resId),
                        contentDescription = "Add Sticker",
                        modifier = Modifier
                            .size(50.dp)
                            .clickable { viewModel.addSticker(resId) }
                    )
                }
            }
            // Clear Button
            IconButton(onClick = { viewModel.clearAll() }) {
                Icon(Icons.Default.Delete, contentDescription = "Clear", tint = Color.White)
            }
        }

        // 5. Bottom Overlay (Caption / Share)
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.6f))
                .padding(16.dp)
                .navigationBarsPadding()
                .imePadding()
        ) {
            TextField(
                value = viewModel.caption.value,
                onValueChange = { viewModel.updateCaption(it) },
                placeholder = { Text("Write a caption...", color = Color.LightGray) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { viewModel.saveAndShare(context, imagePath) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Share, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Share to Stories")
            }
        }
    }
}