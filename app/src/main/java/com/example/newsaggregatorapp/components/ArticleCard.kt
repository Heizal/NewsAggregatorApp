package com.example.newsaggregatorapp.components

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.newsaggregatorapp.helpers.handleArticleClick
import com.example.newsaggregatorapp.models.ArticleEntity
import com.example.newsaggregatorapp.ui.theme.CardBackground
import com.example.newsaggregatorapp.ui.theme.SeparatorColor
import com.example.newsaggregatorapp.util.getTimeAgo
import com.example.newsaggregatorapp.viewmodel.BookmarkViewModel
import com.example.newsaggregatorapp.viewmodel.RecentlyReadViewModel

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
                handleArticleClick(context, article, recentlyReadViewModel)
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

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))
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
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 8.dp),
            thickness = 1.dp,
            color = SeparatorColor
        )
    }
}