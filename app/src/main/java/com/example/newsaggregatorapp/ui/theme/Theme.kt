package com.example.newsaggregatorapp.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Define Custom Colors
private val DarkBlue = Color(0xFF0D1B2A)
private val LightBlue = Color(0xFF1B263B)
private val AccentColor = Color(0xFFE63946)

// Define Light Theme Colors
private val LightColors = lightColorScheme(
    primary = DarkBlue,
    secondary = AccentColor,
    background = Color.White,
    onPrimary = Color.White,
    onBackground = DarkBlue
)

// Define Dark Theme Colors
private val DarkColors = darkColorScheme(
    primary = LightBlue,
    secondary = AccentColor,
    background = DarkBlue,
    onPrimary = Color.White,
    onBackground = Color.White
)

@Composable
fun NewsAppTheme(darkTheme: Boolean = false, content: @Composable () -> Unit) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = CustomTypography,
        content = content
    )
}
