package com.example.newsaggregatorapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.newsaggregatorapp.api.NewsApiService
import com.example.newsaggregatorapp.database.AppDatabase
import com.example.newsaggregatorapp.models.ArticleEntity
import com.example.newsaggregatorapp.repository.RecentSearchRepository
import com.example.newsaggregatorapp.service.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val recentSearchRepo: RecentSearchRepository = RecentSearchRepository(
        AppDatabase.getDatabase(application).recentSearchDao()
    ),
    private val apiService: NewsApiService = RetrofitInstance.getApiService(application),
    private val apiKey: String = RetrofitInstance.getApiKey()
) : AndroidViewModel(application) {

    val recentSearches = recentSearchRepo.recentSearches

    private val _searchResults = MutableStateFlow<List<ArticleEntity>>(savedStateHandle["searchResults"] ?: emptyList())
    val searchResults: StateFlow<List<ArticleEntity>> = _searchResults

    //Fetch news based on user query
    fun searchNews(query: String) {
        viewModelScope.launch {
            try {
                val response = apiService.searchArticles(query = query, apiKey = apiKey)
                _searchResults.value = response.articles
                recentSearchRepo.addSearch(query)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    //Store recent searches
    fun addRecentSearch(query: String) {
        viewModelScope.launch {
            recentSearchRepo.addSearch(query)
        }
    }
}