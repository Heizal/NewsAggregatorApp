package com.example.newsaggregatorapp.repository

import com.example.newsaggregatorapp.database.BookmarkDao
import com.example.newsaggregatorapp.models.ArticleEntity
import kotlinx.coroutines.flow.Flow

class BookmarkRepository (private val bookmarkDao: BookmarkDao) {
    val allBookmarks: Flow<List<ArticleEntity>> = bookmarkDao.getBookmarkedArticles()

    suspend fun addBookmark(article: ArticleEntity) {
        bookmarkDao.addBookmark(article)
    }

    suspend fun removeBookmark(article: ArticleEntity) {
        bookmarkDao.removeBookmark(article)
    }

}