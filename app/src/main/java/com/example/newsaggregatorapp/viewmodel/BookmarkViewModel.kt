package com.example.newsaggregatorapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsaggregatorapp.models.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookmarkViewModel (application: Application) : AndroidViewModel(application) {
    private val _bookmarks = MutableStateFlow<List<Article>>(emptyList())
    val bookmarks: StateFlow<List<Article>> = _bookmarks

    //Add bookmark
    fun addBookmark(article: Article){
        viewModelScope.launch {
            val updatedList = bookmarks.value.toMutableList()
            if (!updatedList.contains(article)){
                updatedList.add(article)
                _bookmarks.value = updatedList
            }
        }
    }

    //Remove bookmark
    fun removeBookmark(article: Article){
        viewModelScope.launch {
            val updatedList = bookmarks.value.toMutableList()
            updatedList.remove(article)
            _bookmarks.value = updatedList
        }
    }
}