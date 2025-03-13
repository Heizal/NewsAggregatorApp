package com.example.newsaggregatorapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.newsaggregatorapp.navigation.Screen

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeScreen(navController: NavController){
    val sampleNews = listOf("Article 1", "Article 2", "Article 3")

    Scaffold (
        topBar = { TopAppBar(title = { Text("News Aggregator" )})}
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ){
            items(sampleNews) { article ->
                Card (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { navController.navigate(Screen.Article.route) },
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Text(text = article, modifier = Modifier.padding(16.dp))

                }

            }
        }

    }
}