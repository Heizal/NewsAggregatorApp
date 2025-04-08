package com.example.newsaggregatorapp.models

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

data class NewsResponse(
    val articles: List<ArticleEntity>
)

@Entity(tableName = "bookmarked_articles")
data class ArticleEntity(
    @PrimaryKey val title: String,
    val author: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val category: String?
) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is ArticleEntity) return false
            return title == other.title
        }

        override fun hashCode(): Int {
            return title.hashCode()
        }
}

@Entity(tableName = "recently_read_articles")
data class RecentlyReadArticleEntity(
    @PrimaryKey val title: String,
    val author: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val category: String?
)
