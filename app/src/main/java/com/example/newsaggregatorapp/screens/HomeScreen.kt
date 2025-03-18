package com.example.newsaggregatorapp.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.newsaggregatorapp.R
import com.example.newsaggregatorapp.models.ArticleEntity
import com.example.newsaggregatorapp.viewmodel.BookmarkViewModel
import com.example.newsaggregatorapp.viewmodel.HomeViewModel
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(), bookmarkViewModel: BookmarkViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val newsState by viewModel.news.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

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
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            // Show Top 5 Latest Articles in Large Cards
            if (newsState.isNotEmpty()) {
                LargeNewsItem(newsState.first(), navController)
            }

            val categories = listOf("general", "business", "entertainment", "health", "science", "sports", "technology")

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                items(categories) { category ->
                    val isSelected = category.lowercase() == selectedCategory.lowercase()
                    Text(
                        text = category.replaceFirstChar { it.uppercase() },
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable { viewModel.setCategory(category.lowercase()) }
                            .background(
                                if (isSelected) Color.Red else Color.Gray,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(10.dp),
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            //News List
            LazyColumn {
                items(newsState.drop(1)) { article ->
                    NewsItem(article, bookmarkViewModel)
                }
            }
        }
    }
}

@Composable
fun LargeNewsItem(article: ArticleEntity, navController: NavController){
    val context = LocalContext.current
    val firstAuthor = article.author?.split(",")?.firstOrNull()?.trim() ?: ""
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                article.url?.let { url ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)
                }
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column {
            // 🖼️ Article Image
            Image(
                painter = rememberAsyncImagePainter(model = article.urlToImage ?: R.drawable.placeholder_background),
                contentDescription = "Top News Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(16.dp)) {
                // 🔥 Category Badge
                Text(
                    text = article.category?.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                    } ?: "General",
                    color = Color.Red,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .background(Color(0xFFFFEBEE), shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 📰 Article Title
                Text(
                    text = article.title ?: "No Title",
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(modifier = Modifier.height(8.dp))

                // ✍️ Author & ⏰ Published Time
                if (firstAuthor.isNotEmpty()){
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = firstAuthor,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = getTimeAgo(article.publishedAt),
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

// 📆 Helper Function to Convert Date to "X minutes/hours ago"
fun getTimeAgo(dateString: String?): String {
    if (dateString.isNullOrEmpty()) return "Unknown time"

    val formatter = DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of("UTC"))
    val time = ZonedDateTime.parse(dateString, formatter)
    val now = ZonedDateTime.now(ZoneId.of("UTC"))

    val diff = Duration.between(time, now)

    return when {
        diff.toMinutes() < 1 -> "Just now"
        diff.toMinutes() < 60 -> "${diff.toMinutes()} minutes ago"
        diff.toHours() < 24 -> "${diff.toHours()} hours ago"
        diff.toDays() < 7 -> "${diff.toDays()} days ago"
        else -> "${diff.toDays() / 7} weeks ago"
    }
}

@Composable
fun NewsItem(article: ArticleEntity, bookmarkViewModel: BookmarkViewModel) {
    val context = LocalContext.current
    val bookmarkedArticles by bookmarkViewModel.bookmarks.collectAsState()
    val isBookmarked = bookmarkedArticles.any { it.title == article.title }
    val firstAuthor = article.author?.split(",")?.firstOrNull()?.trim() ?: ""

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                article.url?.let { url ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)
                }
            },
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
                if (firstAuthor.isNotEmpty()){
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = firstAuthor,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }

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

