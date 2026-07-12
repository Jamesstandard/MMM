package com.aistudio.multimetamatrix.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val LightColorScheme = lightColorScheme(
    primary = Color(0xFF0078d4), // Microsoft Blue
    onPrimary = Color.White,
    secondary = Color(0xFF00B4B4), // Vibrant Cyan/Teal
    onSecondary = Color.White,
    background = Color(0xFFF8F9FA), // Soft off-white
    onBackground = Color(0xFF1F1F1F), // Dark gray
    surface = Color.White,
    onSurface = Color(0xFF1F1F1F),
    surfaceVariant = Color(0xFFEEEEEE),
    onSurfaceVariant = Color(0xFF666666)
)

val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF0078d4),
    onPrimary = Color.White,
    secondary = Color(0xFF00B4B4),
    onSecondary = Color.White,
    background = Color(0xFF1A1A1A),
    onBackground = Color(0xFFEEEEEE),
    surface = Color(0xFF2D2D2D),
    onSurface = Color(0xFFEEEEEE),
    surfaceVariant = Color(0xFF404040),
    onSurfaceVariant = Color(0xFF999999)
)

@Composable
fun MultiMetaMatrixTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
