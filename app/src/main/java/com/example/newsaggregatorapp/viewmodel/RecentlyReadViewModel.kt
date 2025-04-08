package com.example.newsaggregatorapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsaggregatorapp.database.AppDatabase
import com.example.newsaggregatorapp.models.RecentlyReadArticleEntity
import com.example.newsaggregatorapp.repository.RecentlyReadRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class RecentlyReadViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: RecentlyReadRepository
    private val _recentlyRead = MutableStateFlow<List<RecentlyReadArticleEntity>>(emptyList())
    val recentlyRead: StateFlow<List<RecentlyReadArticleEntity>> = _recentlyRead.asStateFlow()

    init {
        repository = createRepository(application)
        observeRecentlyReadArticles()
    }

    private fun createRepository(app: Application): RecentlyReadRepository {
        val dao = AppDatabase.getDatabase(app).recentlyReadDao()
        return RecentlyReadRepository(dao)
    }

    private fun observeRecentlyReadArticles() {
        viewModelScope.launch {
            repository.allRecentlyRead
                .catch { e ->
                    println("Error while collecting recently read articles: ${e.message}")
                }
                .collect { articles ->
                _recentlyRead.value = articles
            }
        }
    }


    fun addToRecentlyRead(article: RecentlyReadArticleEntity) {
        viewModelScope.launch {
            repository.addToRecentlyRead(article)
        }
    }
}