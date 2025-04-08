package com.example.newsaggregatorapp.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.newsaggregatorapp.components.CategoryTabs
import com.example.newsaggregatorapp.components.LargeNewsItem
import com.example.newsaggregatorapp.components.MainScaffold
import com.example.newsaggregatorapp.components.NewsItem
import com.example.newsaggregatorapp.models.ArticleEntity
import com.example.newsaggregatorapp.models.RecentlyReadArticleEntity
import com.example.newsaggregatorapp.ui.theme.CardBackground
import com.example.newsaggregatorapp.ui.theme.SeparatorColor
import com.example.newsaggregatorapp.util.getTimeAgo
import com.example.newsaggregatorapp.viewmodel.BookmarkViewModel
import com.example.newsaggregatorapp.viewmodel.HomeViewModel
import com.example.newsaggregatorapp.viewmodel.RecentlyReadViewModel
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

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


