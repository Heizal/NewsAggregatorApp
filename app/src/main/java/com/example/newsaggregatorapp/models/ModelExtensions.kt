package com.example.newsaggregatorapp.models

fun RecentlyReadArticleEntity.toArticleEntity(): ArticleEntity {
    return ArticleEntity(
        title = this.title,
        author = this.author,
        url = this.url,
        urlToImage = this.urlToImage,
        publishedAt = this.publishedAt,
        category = this.category
    )
}