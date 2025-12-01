package com.example.proyectologin005d

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectologin005d.ui.screens.PostScreen
import com.example.proyectologin005d.viewmodel.PostViewModel
import com.example.proyectologin005d.ui.theme.ApiRestTheme

class MainActivite : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ApiRestTheme {
                AppContent()
            }
        }
    }
}

@Composable
fun AppContent() {
    val postViewModel: PostViewModel = viewModel()
    PostScreen(viewModel = postViewModel)
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    ApiRestTheme {
        AppContent()
    }
}