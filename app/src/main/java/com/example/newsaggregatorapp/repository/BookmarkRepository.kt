package com.example.newsaggregatorapp.repository

import com.example.newsaggregatorapp.database.BookmarkDao
import com.example.newsaggregatorapp.models.ArticleEntity
import kotlinx.coroutines.flow.Flow

class BookmarkRepository (private val bookmarkDao: BookmarkDao) {
    //Get all bookmarked articles
    val allBookmarks: Flow<List<ArticleEntity>> = bookmarkDao.getBookmarkedArticles()

    // ✅ Add a bookmark
    suspend fun addBookmark(article: ArticleEntity) {
        bookmarkDao.addBookmark(article)
    }

    // ✅ Remove a bookmark
    suspend fun removeBookmark(article: ArticleEntity) {
        bookmarkDao.removeBookmark(article)
    }

}