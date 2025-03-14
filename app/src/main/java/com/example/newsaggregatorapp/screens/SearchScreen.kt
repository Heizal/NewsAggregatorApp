package com.example.newsaggregatorapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.newsaggregatorapp.viewmodel.BookmarkViewModel
import com.example.newsaggregatorapp.viewmodel.SearchViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SearchScreen(navController: NavController, searchViewModel: SearchViewModel = viewModel(), bookmarkViewModel: BookmarkViewModel = viewModel()) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val searchResults by searchViewModel.searchResults.collectAsState()
    val recentSearches by searchViewModel.recentSearches.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Search News") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
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
            // 🔍 Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search for news...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                },
                modifier = Modifier.fillMaxWidth(),

                // ✅ Fix: Set Enter Key to "Search"
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),

                // ✅ Fix: Handle Search Button Press
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if (searchQuery.text.isNotEmpty()) {
                            searchViewModel.searchNews(searchQuery.text)
                            searchViewModel.addRecentSearch(searchQuery.text)
                            keyboardController?.hide()
                        }
                    }
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 🔄 Recent Searches
            if (recentSearches.isNotEmpty()) {
                Text("Recent Searches", style = MaterialTheme.typography.titleMedium)
                LazyColumn {
                    items(recentSearches) { query ->
                        Text(
                            text = query,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    searchQuery = TextFieldValue(query)
                                    searchViewModel.searchNews(query)
                                }
                                .padding(8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 📜 Search Results
            if (searchResults.isNotEmpty()) {
                Text("Search Results", style = MaterialTheme.typography.titleMedium)
                LazyColumn {
                    items(searchResults) { article ->
                        NewsItem(article, bookmarkViewModel)
                    }
                }
            }
        }
    }
}
