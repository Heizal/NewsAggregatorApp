package com.example.newsaggregatorapp.screens

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.savedstate.findViewTreeSavedStateRegistryOwner
import com.example.newsaggregatorapp.viewmodel.BookmarkViewModel
import com.example.newsaggregatorapp.viewmodel.SearchViewModel
import com.example.newsaggregatorapp.viewmodel.SearchViewModelFactory
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SearchScreen(
    navController: NavController,
    backStackEntry: NavBackStackEntry,
    bookmarkViewModel: BookmarkViewModel = viewModel()
) {
    val context = LocalContext.current
    val factory = SearchViewModelFactory(
        application = context.applicationContext as Application,
        owner = backStackEntry
    )

    val searchViewModel: SearchViewModel = viewModel(
        viewModelStoreOwner = backStackEntry,
        factory = factory
    )

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
