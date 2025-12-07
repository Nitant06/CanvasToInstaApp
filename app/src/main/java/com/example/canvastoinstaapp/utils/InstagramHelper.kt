package com.example.canvastoinstaapp.utils


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

object InstagramHelper {
    fun shareToStory(context: Context, uri: Uri) {
        val intent = Intent("com.instagram.share.ADD_TO_STORY").apply {
            setDataAndType(uri, "image/*")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            setPackage("com.instagram.android")
        }

        try {
            context.startActivity(intent)
        } catch (_: Exception) {
            Toast.makeText(context, "Instagram not installed", Toast.LENGTH_SHORT).show()
        }
    }
}