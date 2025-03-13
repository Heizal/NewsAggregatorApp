package com.example.newsaggregatorapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.ui.unit.dp


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ArticleScreen(navController: NavController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Article Details") }) }
    ) { paddingValues ->
        Column (modifier = Modifier.padding(paddingValues)){
            Text("This is the full article content.", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
