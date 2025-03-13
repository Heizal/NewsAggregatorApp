package com.example.newsaggregatorapp.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newsaggregatorapp.screens.ArticleScreen
import com.example.newsaggregatorapp.screens.HomeScreen
import com.example.newsaggregatorapp.screens.SettingsScreen


sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Settings : Screen("settings")
    object Article : Screen("article?articleUrl={articleUrl}") {
        fun createRoute(articleUrl: String): String {
            return "article?articleUrl=${Uri.encode(articleUrl)}"
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController){
    NavHost(navController, startDestination = Screen.Home.route){
        composable(Screen.Home.route){ HomeScreen(navController) }

        composable(Screen.Article.route){ backStackEntry ->
            val articleUrl = backStackEntry.arguments?.getString("articleUrl") ?: ""
            ArticleScreen(navController, articleUrl)
        }

        composable(Screen.Settings.route){ SettingsScreen(navController) }

    }
}