package com.example.newsaggregatorapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.newsaggregatorapp.models.Article
import com.example.newsaggregatorapp.navigation.Screen
import com.example.newsaggregatorapp.viewmodel.HomeViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val newsState by viewModel.news.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("News Aggregator") }) }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            //show loading indicator if newsState is empty
            if (newsState.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize())
            } else {
                //Display news articles in a list
                LazyColumn {
                    items(newsState) { article ->
                        NewsItem(article, navController)
                    }
                }
            }
        }
    }
}

@Composable
fun NewsItem(article: Article, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate((Screen.Article.createRoute(article.url)))
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            if (!article.urlToImage.isNullOrEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(article.urlToImage),
                    contentDescription = "Article Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }
            Text(text = article.title, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 8.dp))
            article.description?.let {
                Text(text = it, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(top = 4.dp))
            }
        }
    }
}

