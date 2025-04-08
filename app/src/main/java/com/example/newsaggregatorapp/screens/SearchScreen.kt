package com.example.newsaggregatorapp.screens

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.newsaggregatorapp.components.MainScaffold
import com.example.newsaggregatorapp.components.NewsItem
import com.example.newsaggregatorapp.components.RecentSearchesList
import com.example.newsaggregatorapp.components.SearchBar
import com.example.newsaggregatorapp.viewmodel.BookmarkViewModel
import com.example.newsaggregatorapp.viewmodel.RecentlyReadViewModel
import com.example.newsaggregatorapp.viewmodel.SearchViewModel
import com.example.newsaggregatorapp.viewmodel.SearchViewModelFactory

@Composable
fun SearchScreen(
    navController: NavHostController,
    backStackEntry: NavBackStackEntry,
    bookmarkViewModel: BookmarkViewModel = viewModel(),
    recentlyReadViewModel: RecentlyReadViewModel = viewModel()
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
    val currentRoute = navController.currentBackStackEntry?.destination?.route ?: "search"

    MainScaffold(
        navController = navController,
        currentRoute = currentRoute,
        title = "Saved News"
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = {
                    if (searchQuery.text.isNotEmpty()) {
                        searchViewModel.searchNews(searchQuery.text)
                        searchViewModel.addRecentSearch(searchQuery.text)
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            RecentSearchesList(
                recentSearches = recentSearches,
                onSearchClick = { query ->
                    searchQuery = TextFieldValue(query)
                    searchViewModel.searchNews(query)
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (searchResults.isNotEmpty()) {
                Text("Search Results", style = MaterialTheme.typography.titleMedium)
                LazyColumn {
                    items(searchResults) { article ->
                        NewsItem(
                            article = article,
                            bookmarkViewModel = bookmarkViewModel,
                            recentlyReadViewModel = recentlyReadViewModel
                        )
                    }
                }
            }
        }
    }
}
