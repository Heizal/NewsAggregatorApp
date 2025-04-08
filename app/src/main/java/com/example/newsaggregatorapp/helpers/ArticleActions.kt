package com.example.newsaggregatorapp.helpers

import android.content.Context
import android.content.Intent
import android.net.Uri
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
}