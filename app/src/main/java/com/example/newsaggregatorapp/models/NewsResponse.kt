package com.example.newsaggregatorapp.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

data class NewsResponse(
    val articles: List<ArticleEntity>
)

@Parcelize
@Entity(tableName = "bookmarked_articles")
data class ArticleEntity(
    @PrimaryKey val title: String,
    val author: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val category: String?
) : Parcelable

@Entity(tableName = "recently_read_articles")
data class RecentlyReadArticleEntity(
    @PrimaryKey val title: String,
    val author: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val category: String?
)

@Entity(tableName = "recent_searches")
data class RecentSearchEntity(
    @PrimaryKey val query: String,
    val timestamp: Long = System.currentTimeMillis()
)

