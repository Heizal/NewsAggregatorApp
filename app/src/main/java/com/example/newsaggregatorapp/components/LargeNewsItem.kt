package com.example.newsaggregatorapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.newsaggregatorapp.helpers.handleArticleClick
import com.example.newsaggregatorapp.models.ArticleEntity
import com.example.newsaggregatorapp.ui.theme.CardBackground
import com.example.newsaggregatorapp.util.getTimeAgo
import com.example.newsaggregatorapp.viewmodel.RecentlyReadViewModel

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
                handleArticleClick(context, article, recentlyReadViewModel)
            },
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            Image(
                painter = rememberAsyncImagePainter(model = article.urlToImage ?: ""),
                contentDescription = "News Image",
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
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

                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

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