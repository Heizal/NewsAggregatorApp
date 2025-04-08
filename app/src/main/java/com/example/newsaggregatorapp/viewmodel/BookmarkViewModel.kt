package com.example.newsaggregatorapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsaggregatorapp.database.AppDatabase
import com.example.newsaggregatorapp.models.ArticleEntity
import com.example.newsaggregatorapp.repository.BookmarkRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class BookmarkViewModel (application: Application) : AndroidViewModel(application) {
    private val repository: BookmarkRepository

    private val _bookmarks = MutableStateFlow<List<ArticleEntity>>(emptyList())
    val bookmarks: StateFlow<List<ArticleEntity>> = _bookmarks.asStateFlow()

    init {
        val dao = AppDatabase.getDatabase(application).bookmarkDao()
        repository = BookmarkRepository(dao)

        observeBookmarks()
    }

    private fun observeBookmarks() {
        viewModelScope.launch {
            repository.allBookmarks
                .catch { e ->
                    println("Error while collecting bookmarks: ${e.message}")
                }
                .collect { articles ->
                _bookmarks.value = articles
            }
        }
    }


    //Add bookmark
    fun addBookmark(article: ArticleEntity){
        viewModelScope.launch {
            repository.addBookmark(article)
        }
    }

    //Remove bookmark
    fun removeBookmark(article: ArticleEntity){
        viewModelScope.launch {
            repository.removeBookmark(article)
        }
    }

    fun isBookmarked(article: ArticleEntity): Boolean {
        return _bookmarks.value.any { it.title == article.title }
    }
}