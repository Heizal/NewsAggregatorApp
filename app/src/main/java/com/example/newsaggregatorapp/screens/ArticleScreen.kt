package com.example.newsaggregatorapp.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.compose.ui.unit.dp


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ArticleScreen(navController: NavController, articleUrl: String) {
    val context = LocalContext.current
    Scaffold(
        topBar = { TopAppBar(title = { Text("Article Details") },
            navigationIcon = { IconButton(onClick = {navController.popBackStack()}) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription =  "Back")
            }}
            ) }
    ) { paddingValues ->
        Column (modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp)){

            Text("Read the full article below:")
            Button(onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(articleUrl))
                context.startActivity(intent)
            }, modifier = Modifier.padding(top = 16.dp)) {
                Text("Read Full Article")
            }
        }
    }
}
