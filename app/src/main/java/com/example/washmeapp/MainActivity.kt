package com.example.washmeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.washmeapp.ui.navigation.SetupNavigation
import com.example.washmeapp.ui.theme.WashMeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WashMeAppTheme {
                val navController = rememberNavController()
                SetupNavigation(navController = navController)
            }
        }
    }
}