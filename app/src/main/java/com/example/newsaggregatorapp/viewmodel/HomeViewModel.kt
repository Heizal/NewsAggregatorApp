package com.example.newsaggregatorapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsaggregatorapp.models.ArticleEntity
import com.example.newsaggregatorapp.service.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val _news = MutableStateFlow<List<ArticleEntity>>(emptyList())
    val news: StateFlow<List<ArticleEntity>> = _news

    private val apiService = RetrofitInstance.getApiService(application)
    private val apiKey = RetrofitInstance.getApiKey()

    var selectedCategory = MutableStateFlow("general")

    init {
        fetchNews("general")
    }

   fun fetchNews(category: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getTopHeadlines(category = category, apiKey = apiKey)
                _news.value = mapToArticleEntities(response.articles, category)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
   }

    fun setCategory(category: String){
        selectedCategory.value = category
        fetchNews(category)
    }

    private fun mapToArticleEntities(articles: List<ArticleEntity>, category: String): List<ArticleEntity> {
        return articles.map {
            ArticleEntity(
                title = it.title ?: "No Title",
                author = it.author ?: "Unknown",
                publishedAt = it.publishedAt ?: "",
                url = it.url,
                urlToImage = it.urlToImage,
                category = category
            )
        }
    }
}