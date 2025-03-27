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
import kotlinx.coroutines.launch

class RecentlyReadViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: RecentlyReadRepository
    private val _recentlyRead = MutableStateFlow<List<RecentlyReadArticleEntity>>(emptyList())
    val recentlyRead: StateFlow<List<RecentlyReadArticleEntity>> = _recentlyRead.asStateFlow()

    init {
        val dao = AppDatabase.getDatabase(application).recentlyReadDao()
        repository = RecentlyReadRepository(dao)

        viewModelScope.launch {
            repository.allRecentlyRead.collect { articles ->
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