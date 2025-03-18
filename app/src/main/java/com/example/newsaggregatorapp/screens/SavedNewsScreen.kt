package com.example.newsaggregatorapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.newsaggregatorapp.viewmodel.BookmarkViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SavedNewsScreen(navController: NavController, bookmarkViewModel: BookmarkViewModel = viewModel()) {
    val savedArticles by bookmarkViewModel.bookmarks.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Saved News") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            println("Saved Articles Count: ${savedArticles.size}")
            if (savedArticles.isEmpty()) {
                Text("No saved articles.", style = MaterialTheme.typography.bodyLarge)
            } else {
                LazyColumn {
                    items(savedArticles) { article ->
                        NewsItem(article, bookmarkViewModel)
                    }
                }
            }
        }
    }
}