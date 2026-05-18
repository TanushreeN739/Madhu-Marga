package com.example.madhu_marga.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = HoneyYellow,
    secondary = Amber,
    tertiary = NatureGreen,
    background = BeeBlack,
    surface = DarkBrown,
    onPrimary = BeeBlack,
    onSecondary = BeeBlack,
    onTertiary = White,
    onBackground = White,
    onSurface = White
)

private val LightColorScheme = lightColorScheme(
    primary = HoneyYellow,
    secondary = Amber,
    tertiary = NatureGreen,
    background = LightHoney,
    surface = White,
    onPrimary = BeeBlack,
    onSecondary = BeeBlack,
    onTertiary = White,
    onBackground = BeeBlack,
    onSurface = BeeBlack
)

@Composable
fun MadhuMargaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}