package com.example.newsaggregatorapp.repository

import com.example.newsaggregatorapp.database.RecentlyReadDao
import com.example.newsaggregatorapp.models.RecentlyReadArticleEntity
import kotlinx.coroutines.flow.Flow

class RecentlyReadRepository(private val recentlyReadDao: RecentlyReadDao) {
    val allRecentlyRead: Flow<List<RecentlyReadArticleEntity>> = recentlyReadDao.getRecentlyRead()

    suspend fun addToRecentlyRead(article: RecentlyReadArticleEntity) {
        recentlyReadDao.insert(article)
    }
}