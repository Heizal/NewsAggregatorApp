package com.example.newsaggregatorapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.newsaggregatorapp.models.ArticleEntity
import com.example.newsaggregatorapp.viewmodel.BookmarkViewModel
import com.example.newsaggregatorapp.viewmodel.HomeViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(), bookmarkViewModel: BookmarkViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val newsState by viewModel.news.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("News Aggregator") },
                actions = {
                    // 🔍 Search Icon - Navigates to `SearchScreen`
                    IconButton(onClick = { navController.navigate("search") }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                IconButton(onClick = { navController.navigate("home") }) {
                    Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { navController.navigate("saved_news") }) {
                    Icon(imageVector = Icons.Default.Bookmark, contentDescription = "Saved News")
                }
            }
        }

    )
    { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            if (newsState.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize())
            } else {
                LazyColumn {
                    items(newsState) { article ->
                        NewsItem(article, bookmarkViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun NewsItem(article: ArticleEntity, bookmarkViewModel: BookmarkViewModel) {
    val bookmarkedArticles by bookmarkViewModel.bookmarks.collectAsState()
    val isBookmarked = bookmarkedArticles.any { it.title == article.title }

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { /* Open in browser */ },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row (modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Article Image
        Image(painter = rememberAsyncImagePainter(model = article.urlToImage ?: ""),
                contentDescription = "Article Image",
                modifier = Modifier
                    .size(100.dp)
                    .padding(4.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            // 📜 Article Details (Title, Author, Date)
            Column(modifier = Modifier.weight(1f)) {
                // 📰 Title
                Text(
                    text = article.title ?: "No Title",
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                // ✍️ Author & 📆 Date
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = article.author ?: "Unknown",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }

            // 🔖 Bookmark Button
            IconButton(
                onClick = {
                    if (isBookmarked) {
                        bookmarkViewModel.removeBookmark(article)
                        println("Removing bookmark: ${article.title}")
                    } else {
                        bookmarkViewModel.addBookmark(article)
                        println("Adding bookmark: ${article.title}")
                    }
                }
            ) {
                Icon(
                    imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                    contentDescription = "Bookmark",
                    tint = if (isBookmarked) Color.Blue else Color.Gray
                )
            }
        }
    }
}

