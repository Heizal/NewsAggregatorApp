package com.example.newsaggregatorapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.newsaggregatorapp.components.CategoryTabs
import com.example.newsaggregatorapp.components.LargeNewsItem
import com.example.newsaggregatorapp.components.MainScaffold
import com.example.newsaggregatorapp.components.NewsItem
import com.example.newsaggregatorapp.viewmodel.BookmarkViewModel
import com.example.newsaggregatorapp.viewmodel.HomeViewModel
import com.example.newsaggregatorapp.viewmodel.RecentlyReadViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    bookmarkViewModel: BookmarkViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    recentlyReadViewModel: RecentlyReadViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val newsState by viewModel.articles.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val currentRoute = navController.currentBackStackEntry?.destination?.route ?: "home"

    MainScaffold(
        navController = navController,
        currentRoute = currentRoute,
        title = "News Aggregator",
        showBackButton = false,
        actions = {
            IconButton(onClick = { navController.navigate("search") }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
        }
    )
    { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            if (newsState.isNotEmpty()) {
                LargeNewsItem(article = newsState.first(), recentlyReadViewModel = recentlyReadViewModel, navController = navController)
            }

            val categories = listOf("general", "business", "entertainment", "health", "science", "sports", "technology")

            CategoryTabs(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = { viewModel.setCategory(it) }
            )
            LazyColumn {
                items(newsState.drop(1)) { article ->
                    NewsItem(article, bookmarkViewModel, recentlyReadViewModel)
                }
            }
        }
    }
}


