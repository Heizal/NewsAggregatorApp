package com.example.newsaggregatorapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsaggregatorapp.models.ArticleEntity
import com.example.newsaggregatorapp.service.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private val _searchResults = MutableStateFlow<List<ArticleEntity>>(emptyList())
    val searchResults: StateFlow<List<ArticleEntity>> = _searchResults

    private val _recentSearches = MutableStateFlow<List<String>>(emptyList())
    val recentSearches: StateFlow<List<String>> = _recentSearches

    // ✅ Same approach as HomeViewModel
    private val apiService = RetrofitInstance.getApiService(application)
    private val apiKey = RetrofitInstance.getApiKey()

    // 🔍 Fetch news based on user query
    fun searchNews(query: String) {
        viewModelScope.launch {
            try {
                val response = apiService.searchArticles(query = query, apiKey = apiKey)
                _searchResults.value = response.articles
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // 📝 Store recent searches
    fun addRecentSearch(query: String) {
        viewModelScope.launch {
            val updatedList = _recentSearches.value.toMutableList()
            if (!updatedList.contains(query)) {
                updatedList.add(0, query) // Add new query at the top
                if (updatedList.size > 5) updatedList.removeAt(updatedList.size - 1) // Keep only last 5 searches
                _recentSearches.value = updatedList
            }
        }
    }
}