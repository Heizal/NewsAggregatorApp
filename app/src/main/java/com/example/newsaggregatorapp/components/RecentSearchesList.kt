package com.example.newsaggregatorapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newsaggregatorapp.models.RecentSearchEntity

@Composable
fun RecentSearchesList(
    recentSearches: List<RecentSearchEntity>,
    onSearchClick: (String) -> Unit
) {
    if (recentSearches.isNotEmpty()) {
        Text("Recent Searches", style = MaterialTheme.typography.titleMedium)
        LazyColumn {
            items(recentSearches) { search ->
                val query = search.query
                Text(
                    text = query,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSearchClick(query) }
                        .padding(8.dp)
                )
            }
        }
    }
}