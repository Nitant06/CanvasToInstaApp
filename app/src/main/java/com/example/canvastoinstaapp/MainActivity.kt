package com.example.canvastoinstaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.canvastoinstaapp.ui.navigation.AppNavigation
import com.example.canvastoinstaapp.ui.theme.CanvasToInstaAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CanvasToInstaAppTheme {
                AppNavigation()
            }
        }
    }
}
