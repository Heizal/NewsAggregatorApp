package com.example.newsaggregatorapp.repository

import androidx.room.Query
import com.example.newsaggregatorapp.database.RecentSearchDao
import com.example.newsaggregatorapp.models.RecentSearchEntity
import kotlinx.coroutines.flow.Flow

class RecentSearchRepository(private val dao: RecentSearchDao) {
    val recentSearches: Flow<List<RecentSearchEntity>> = dao.getRecentSearches()

    suspend fun addSearch(query: String){
        dao.insertSearch(RecentSearchEntity(query))
        dao.pruneOldSearches()
    }
}