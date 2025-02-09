package com.example.pokemonappv1.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext



// Light color scheme
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6200EE), // Purple
    onPrimary = Color(0xFFFFFFFF), // White
    secondary = Color(0xFF03DAC6), // Teal
    onSecondary = Color(0xFF000000), // Black
    tertiary = Color(0xFF3700B3), // Dark Purple
    onTertiary = Color(0xFFFFFFFF), // White
    background = Color(0xFFFFFFFF), // White
    onBackground = Color(0xFF000000), // Black
    surface = Color(0xFFFFFFFF), // White
    onSurface = Color(0xFF000000), // Black
    error = Color(0xFFB00020), // Red
    onError = Color(0xFFFFFFFF), // White
)

// Dark color scheme
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFBB86FC), // Light Purple
    onPrimary = Color(0xFF000000), // Black
    secondary = Color(0xFF03DAC6), // Teal
    onSecondary = Color(0xFF000000), // Black
    tertiary = Color(0xFF3700B3), // Dark Purple
    onTertiary = Color(0xFFFFFFFF), // White
    background = Color(0xFF121212), // Dark Gray
    onBackground = Color(0xFFFFFFFF), // White
    surface = Color(0xFF121212), // Dark Gray
    onSurface = Color(0xFFFFFFFF), // White
    error = Color(0xFFCF6679), // Light Red
    onError = Color(0xFF000000), // Black
)
    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */


@Composable
fun PokemonAppV1Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}