package com.example.newsaggregatorapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsaggregatorapp.models.Article
import com.example.newsaggregatorapp.service.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val _news = MutableStateFlow<List<Article>>(emptyList())
    val news: StateFlow<List<Article>> = _news

    init {
        fetchNews()
    }

    private fun fetchNews() {
        // Fetch news from the API
        viewModelScope.launch {
            try{
                val response = RetrofitInstance.api.getTopHeadlines()
                _news.value = response.articles
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}