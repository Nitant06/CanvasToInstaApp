package com.example.canvastoinstaapp.ui.camera

import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import java.io.File
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun CameraScreen(onImageCaptured: (String) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    var imageCapture by remember { mutableStateOf<ImageCapture?>(null) }
    var hasPermission by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { hasPermission = it }
    )

    LaunchedEffect(Unit) { launcher.launch(Manifest.permission.CAMERA) }

    if (hasPermission) {
        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = { ctx ->
                    val previewView = PreviewView(ctx)
                    val providerFuture = ProcessCameraProvider.getInstance(ctx)
                    providerFuture.addListener({
                        val provider = providerFuture.get()
                        val preview = Preview.Builder().build()
                        imageCapture = ImageCapture.Builder().build()
                        preview.setSurfaceProvider(previewView.surfaceProvider)
                        try {
                            provider.unbindAll()
                            provider.bindToLifecycle(
                                lifecycleOwner,
                                CameraSelector.DEFAULT_BACK_CAMERA,
                                preview,
                                imageCapture
                            )
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }, ContextCompat.getMainExecutor(ctx))
                    previewView
                },
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 50.dp)
                    .size(80.dp)
                    .background(Color.White, CircleShape)
                    .border(4.dp, Color.Gray, CircleShape)
                    .clickable { takePhoto(context, imageCapture, onImageCaptured) }
            )
        }
    } else {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) { Text("Camera Permission Required") }
    }
}

private fun takePhoto(context: Context, capture: ImageCapture?, onImageSaved: (String) -> Unit) {
    val photoFile = File(context.cacheDir, "captured_img_${System.currentTimeMillis()}.jpg")
    capture?.takePicture(
        ImageCapture.OutputFileOptions.Builder(photoFile).build(),
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                val encodedPath =
                    URLEncoder.encode(photoFile.absolutePath, StandardCharsets.UTF_8.toString())
                onImageSaved(encodedPath)
            }

            override fun onError(exc: ImageCaptureException) {
                Toast.makeText(context, "Error: ${exc.message}", Toast.LENGTH_SHORT).show()
            }
        }
    )
}