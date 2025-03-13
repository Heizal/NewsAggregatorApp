package com.example.newsaggregatorapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsaggregatorapp.models.Article
import com.example.newsaggregatorapp.service.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val _news = MutableStateFlow<List<Article>>(emptyList())
    val news: StateFlow<List<Article>> = _news

    private val apiService = RetrofitInstance.getApiService(application)
    private val apiKey = RetrofitInstance.getApiKey()

    init {
        fetchNews()
    }

    private fun fetchNews() {
        viewModelScope.launch {
            try {
                val response = apiService.getTopHeadlines(apiKey = apiKey)
                _news.value = response.articles
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}