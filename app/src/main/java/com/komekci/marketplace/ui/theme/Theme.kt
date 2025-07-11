package com.komekci.marketplace.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import io.github.alexzhirkevich.cupertino.CupertinoSearchTextField
import io.github.alexzhirkevich.cupertino.theme.CupertinoTheme

private val DarkColorScheme = darkColorScheme(
    primary = accentColor,
    secondary = logoColor,
    tertiary = darkGreen,
    background = background,
    surface = surface,
    error = errorColor,
    onPrimary = background,
    onSurface = blackColor,
    onSecondary = textColor
)

private val LightColorScheme = lightColorScheme(
    primary = accentColor,
    secondary = logoColor,
    tertiary = darkGreen,
    background = background,
    surface = surface,
    error = errorColor,
    onPrimary = background,
    onSurface = blackColor,
    onSecondary = textColor
)

@Composable
fun MarketPlaceTheme(
    darkTheme: Boolean = false,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
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
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.White.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

   

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = {
            CupertinoTheme(
                colorScheme = io.github.alexzhirkevich.cupertino.theme.lightColorScheme()
            ) {
                content()
            }
        }
    )
}