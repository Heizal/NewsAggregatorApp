package com.example.newsaggregatorapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newsaggregatorapp.screens.HomeScreen
import com.example.newsaggregatorapp.screens.RecentlyReadArticlesScreen
import com.example.newsaggregatorapp.screens.SavedNewsScreen
import com.example.newsaggregatorapp.screens.SearchScreen


sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Search : Screen("search")
    object SavedNews : Screen("saved_news")
    object RecentlyRead : Screen("recently_read")
}

@Composable
fun AppNavigation(navController: NavHostController){

    NavHost(navController, startDestination = Screen.Home.route){
        composable(Screen.Home.route){ HomeScreen(navController) }
        composable(Screen.Search.route) { backStackEntry ->
            SearchScreen(navController, backStackEntry)
        }
        composable(Screen.SavedNews.route) { SavedNewsScreen(navController) }
        composable(Screen.RecentlyRead.route) { RecentlyReadArticlesScreen(navController) }

    }
}