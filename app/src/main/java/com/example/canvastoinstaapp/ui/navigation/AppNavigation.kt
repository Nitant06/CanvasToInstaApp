package com.example.canvastoinstaapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.canvastoinstaapp.ui.camera.CameraScreen
import com.example.canvastoinstaapp.ui.editor.EditorScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "camera") {
        composable("camera") {
            CameraScreen(onImageCaptured = { path -> navController.navigate("editor/$path") })
        }
        composable(
            route = "editor/{path}",
            arguments = listOf(navArgument("path") { type = NavType.StringType })
        ) { backStackEntry ->
            val path = backStackEntry.arguments?.getString("path") ?: ""
            EditorScreen(
                imagePath = path,
                onBack = { navController.popBackStack()
                }
            )
        }
    }
}