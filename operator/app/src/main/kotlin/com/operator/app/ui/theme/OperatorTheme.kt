package com.operator.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Retro-futuristic palette: warm near-black, amber indicator light, cream text.
 * Broad mid-century evocation only; no franchise assets.
 */
object OperatorColors {
    val Background = Color(0xFF0F0E0C)
    val Surface = Color(0xFF1A1814)
    val SurfaceRaised = Color(0xFF242119)
    val Amber = Color(0xFFE8B04B)
    val AmberDim = Color(0xFF8C6A2B)
    val Cream = Color(0xFFE9DEC5)
    val CreamDim = Color(0xFF9C927D)
    val Signal = Color(0xFF7FB77E)
    val Alert = Color(0xFFD9534F)
    val Outline = Color(0xFF3A362C)
}

private val ColorScheme = darkColorScheme(
    primary = OperatorColors.Amber,
    onPrimary = OperatorColors.Background,
    secondary = OperatorColors.Cream,
    onSecondary = OperatorColors.Background,
    background = OperatorColors.Background,
    onBackground = OperatorColors.Cream,
    surface = OperatorColors.Surface,
    onSurface = OperatorColors.Cream,
    surfaceVariant = OperatorColors.SurfaceRaised,
    onSurfaceVariant = OperatorColors.CreamDim,
    error = OperatorColors.Alert,
    onError = OperatorColors.Cream,
    outline = OperatorColors.Outline,
)

private val OperatorTypography = Typography(
    displaySmall = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp,
        letterSpacing = 8.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 2.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        letterSpacing = 1.5.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontSize = 14.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontSize = 12.sp,
    ),
)

@Composable
fun OperatorTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ColorScheme,
        typography = OperatorTypography,
        content = content,
    )
}
