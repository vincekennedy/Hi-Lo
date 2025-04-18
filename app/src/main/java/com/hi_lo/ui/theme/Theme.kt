package com.hi_lo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
  primary = Color(0xFF81C784), // Muted Golf Green
  secondary = Color(0xFFFFD54F), // Sand Accent
  background = Color(0xFF1B5E20), // Dark Green
  surface = Color(0xFF2E7D32), // Darker Green
  onPrimary = Color.Black,
  onSecondary = Color.Black,
  onBackground = Color.White,
  onSurface = Color.White,
)

private val LightColorPalette = lightColors(
  primary = Color(0xFF4CAF50), // Golf Green
  secondary = Color(0xFFFFC107), // Sand
  background = Color(0xFFE8F5E9), // Light Grass Green
  surface = Color.White,
  onPrimary = Color.White,
  onSecondary = Color.Black,
  onBackground = Color.Black,
  onSurface = Color.Black,
)

@Composable
fun HiLoTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
  val colors = if (darkTheme) {
    DarkColorPalette
  } else {
    LightColorPalette
  }

  MaterialTheme(
    colors = colors,
    typography = Typography,
    shapes = Shapes,
    content = content
  )
}