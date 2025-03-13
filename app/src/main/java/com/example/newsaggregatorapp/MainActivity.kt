package com.example.newsaggregatorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.newsaggregatorapp.navigation.AppNavigation
import com.example.newsaggregatorapp.ui.theme.NewsAggregatorAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAggregatorAppTheme {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }
}