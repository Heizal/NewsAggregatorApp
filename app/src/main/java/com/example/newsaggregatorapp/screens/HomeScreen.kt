package com.example.newsaggregatorapp.screens

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
import com.example.newsaggregatorapp.components.MainScaffold
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
                LargeNewsItem(newsState.first(), navController, recentlyReadViewModel)
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

@Composable
fun LargeNewsItem(article: ArticleEntity, navController: NavController, recentlyReadViewModel: RecentlyReadViewModel) {
    val context = LocalContext.current
    val firstAuthor = article.author?.split(",")?.firstOrNull()?.trim() ?: ""

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 180.dp, max = 220.dp) // 👈 Limit height range
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable {
                article.url?.let { url ->
                    //Save to recently read
                    recentlyReadViewModel.addToRecentlyRead(
                        RecentlyReadArticleEntity(
                            title = article.title ?: "No Title",
                            author = article.author,
                            url = article.url,
                            urlToImage = article.urlToImage,
                            publishedAt = article.publishedAt,
                            category = article.category
                        )
                    )
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)
                }
            },
        colors = CardDefaults.cardColors(
            containerColor = CardBackground // 👈 White background
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            // 🖼️ Left-aligned image (square thumbnail style)
            Image(
                painter = rememberAsyncImagePainter(model = article.urlToImage ?: ""),
                contentDescription = "News Image",
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            // 📰 Content Column
            Column(modifier = Modifier.weight(1f)) {
                // 🔥 Category
                article.category?.let { category ->
                    Text(
                        text = category.replaceFirstChar { it.uppercase() },
                        color = Color.Red,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier
                            .background(Color(0xFFFFE0E0), RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Title
                Text(
                    text = article.title ?: "No Title",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Author + Time
                if (firstAuthor.isNotEmpty()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = firstAuthor,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(6.dp))
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

@Composable
fun NewsItem(article: ArticleEntity, bookmarkViewModel: BookmarkViewModel, recentlyReadViewModel: RecentlyReadViewModel) {
    val context = LocalContext.current
    val bookmarkedArticles by bookmarkViewModel.bookmarkedArticles.collectAsState()
    val isBookmarked = bookmarkedArticles.any { it.title == article.title }
    val firstAuthor = article.author?.split(",")?.firstOrNull()?.trim() ?: ""

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable {
                article.url?.let { url ->
                    //Save to recently read
                    recentlyReadViewModel.addToRecentlyRead(
                        RecentlyReadArticleEntity(
                            title = article.title ?: "No Title",
                            author = article.author,
                            url = article.url,
                            urlToImage = article.urlToImage,
                            publishedAt = article.publishedAt,
                            category = article.category
                        )
                    )
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)
                }
            },
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Article Image
        Image(painter = rememberAsyncImagePainter(model = article.urlToImage ?: ""),
                contentDescription = "Article Image",
                modifier = Modifier
                    .size(80.dp)
                    .background(Color.LightGray, RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            // 📜 Article Details (Title, Author, Date)
            Column(modifier = Modifier.weight(1f)) {
                // 📰 Title
                Text(
                    text = article.title ?: "No Title",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))
                // ✍️ Author & 📆 Date
                Row (
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    if (firstAuthor.isNotEmpty()) {
                        Text(
                            text = firstAuthor,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }

                    Text(
                        text = getTimeAgo(article.publishedAt),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
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
        // ➖ Separator line
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 8.dp),
            thickness = 1.dp,
            color = SeparatorColor
        )
    }
}

@Composable
fun RecentlyReadNewsItem(article: RecentlyReadArticleEntity) {
    val context = LocalContext.current

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
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberAsyncImagePainter(model = article.urlToImage ?: ""),
                contentDescription = "Article Image",
                modifier = Modifier
                    .size(100.dp)
                    .padding(4.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = article.title ?: "No Title",
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                val author = article.author?.split(",")?.firstOrNull()?.trim().orEmpty()
                if (author.isNotEmpty()) {
                    Text(
                        text = author,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}



