package com.example.newsaggregatorapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.newsaggregatorapp.components.MainScaffold
import com.example.newsaggregatorapp.components.NewsItem
import com.example.newsaggregatorapp.viewmodel.BookmarkViewModel
import com.example.newsaggregatorapp.viewmodel.RecentlyReadViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SavedNewsScreen(navController: NavHostController, bookmarkViewModel: BookmarkViewModel = viewModel(), recentlyReadViewModel: RecentlyReadViewModel = viewModel()) {
    val savedArticles by bookmarkViewModel.bookmarkedArticles.collectAsState()
    val currentRoute = navController.currentBackStackEntry?.destination?.route ?: "saved_news"
    MainScaffold(
        navController = navController,
        currentRoute = currentRoute,
        title = "Saved News"
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            println("Saved Articles Count: ${savedArticles.size}")
            if (savedArticles.isEmpty()) {
                Text("No saved articles.", style = MaterialTheme.typography.bodyLarge)
            } else {
                LazyColumn {
                    items(savedArticles) { article ->
                        NewsItem(
                            article = article,
                            bookmarkViewModel = bookmarkViewModel,
                            recentlyReadViewModel = recentlyReadViewModel
                        )
                    }
                }
            }
        }
    }
}