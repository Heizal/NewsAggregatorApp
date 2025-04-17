package com.example.newsaggregatorapp.helpers

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import com.example.newsaggregatorapp.models.ArticleEntity
import com.example.newsaggregatorapp.models.RecentlyReadArticleEntity
import com.example.newsaggregatorapp.viewmodel.RecentlyReadViewModel

fun handleArticleClick(
    context: Context,
    article: ArticleEntity,
    recentlyReadViewModel: RecentlyReadViewModel
) {
    article.url?.let { url ->
        recentlyReadViewModel.addToRecentlyRead(
            RecentlyReadArticleEntity(
                title = article.title,
                author = article.author,
                url = article.url,
                urlToImage = article.urlToImage,
                publishedAt = article.publishedAt,
                category = article.category
            )
        )
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        context.startActivity(intent)
    }
}