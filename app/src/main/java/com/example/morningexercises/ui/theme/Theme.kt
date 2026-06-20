package com.example.morningexercises.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF4ECDC4),
    secondary = Color(0xFFFF6B9D),
    tertiary = Color(0xFFFFA500)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF4ECDC4),
    secondary = Color(0xFFFF6B9D),
    tertiary = Color(0xFFFFA500)
)

@Composable
fun MorningExercisesAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
