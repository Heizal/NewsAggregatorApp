package com.example.newsaggregatorapp.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newsaggregatorapp.screens.ArticleScreen
import com.example.newsaggregatorapp.screens.HomeScreen
import com.example.newsaggregatorapp.screens.SavedNewsScreen
import com.example.newsaggregatorapp.screens.SearchScreen
import com.example.newsaggregatorapp.screens.SettingsScreen


sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Settings : Screen("settings")
    object Search : Screen("search")

    object SavedNews : Screen("saved_news")
}

@Composable
fun AppNavigation(navController: NavHostController){
    NavHost(navController, startDestination = Screen.Home.route){
        composable(Screen.Home.route){ HomeScreen(navController) }
        composable(Screen.Settings.route){ SettingsScreen(navController) }
        composable(Screen.Search.route) { SearchScreen(navController) }
        composable(Screen.SavedNews.route) { SavedNewsScreen(navController) }

    }
}