package com.example.newsaggregatorapp.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.newsaggregatorapp.screens.CustomBottomBar

@Composable
@OptIn(ExperimentalMaterial3Api::class)

fun MainScaffold(
    navController: NavHostController,
    currentRoute: String,
    title: String,
    showBackButton: Boolean = false,
    actions: @Composable (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    if (showBackButton) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                },
                actions = {
                    actions?.invoke()
                }
            )
        },
        bottomBar = {
            CustomBottomBar(navController = navController, selectedRoute = currentRoute)
        },
        content = content
    )
}
