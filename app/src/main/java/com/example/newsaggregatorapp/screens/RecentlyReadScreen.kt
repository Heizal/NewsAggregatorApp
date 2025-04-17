package com.example.newsaggregatorapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.newsaggregatorapp.components.MainScaffold
import com.example.newsaggregatorapp.components.NewsItem
import com.example.newsaggregatorapp.models.toArticleEntity
import com.example.newsaggregatorapp.viewmodel.BookmarkViewModel
import com.example.newsaggregatorapp.viewmodel.RecentlyReadViewModel

@Composable
fun RecentlyReadArticlesScreen(
    navController: NavHostController,
    recentlyReadViewModel: RecentlyReadViewModel = viewModel(),
    bookmarkViewModel: BookmarkViewModel = viewModel()
) {
    val recentlyReadArticles by recentlyReadViewModel.recentlyRead.collectAsState()
    val currentRoute = navController.currentBackStackEntry?.destination?.route ?: "recently_read"

    MainScaffold(
        navController = navController,
        currentRoute = currentRoute,
        title = "Recently Read"
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (recentlyReadArticles.isEmpty()) {
                Text("No recently read articles.", style = MaterialTheme.typography.bodyLarge)
            } else {
                LazyColumn {
                    items(recentlyReadArticles) { article ->
                        NewsItem(
                            article = article.toArticleEntity(),
                            bookmarkViewModel = bookmarkViewModel,
                            recentlyReadViewModel = recentlyReadViewModel
                        )
                    }
                }
            }
        }
    }
}