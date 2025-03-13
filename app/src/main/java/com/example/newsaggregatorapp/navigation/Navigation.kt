package com.example.newsaggregatorapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newsaggregatorapp.screens.ArticleScreen
import com.example.newsaggregatorapp.screens.HomeScreen
import com.example.newsaggregatorapp.screens.SettingsScreen


sealed class Screen (val route: String) {
    object Home: Screen("home")
    object Article: Screen("article")
    object Settings: Screen("settings")

}

@Composable
fun AppNavigation(navController: NavHostController){
    NavHost(navController, startDestination = Screen.Home.route){
        composable(Screen.Home.route){ HomeScreen(navController) }
        composable(Screen.Article.route){ ArticleScreen(navController) }
        composable(Screen.Settings.route){ SettingsScreen(navController) }

    }
}