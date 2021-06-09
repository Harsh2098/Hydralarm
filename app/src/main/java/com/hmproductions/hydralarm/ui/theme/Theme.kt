package com.hmproductions.hydralarm.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val CustomDarkColorPalette = HydralarmColors(
    neutralFontColor = Color.White,
    cardBorderColor = Color.White,
    cardBackgroundColor = DarkCardBackgroundColor
)

private val CustomLightColorPalette = HydralarmColors(
    neutralFontColor = Color.Black,
    cardBorderColor = Color.DarkGray,
    cardBackgroundColor = LightCardBackgroundColor
)

private val SystemDarkColorPalette = darkColors(
    primary = DarkToolBar,
    primaryVariant = DarkStatusBar,
    surface = Color.Gray
)

private val SystemLightColorPalette = lightColors(
    primary = LightToolBar,
    primaryVariant = LightStatusBar,
    surface = Color.White
)

@Stable
class HydralarmColors(
    neutralFontColor: Color,
    cardBorderColor: Color,
    cardBackgroundColor: Color,
) {
    var cardBorderColor by mutableStateOf(cardBorderColor)
        private set
    var neutralFontColor by mutableStateOf(neutralFontColor)
        private set
    var cardBackgroundColor by mutableStateOf(cardBackgroundColor)
        private set

    fun update(other: HydralarmColors) {
        cardBorderColor = other.cardBorderColor
        neutralFontColor = other.neutralFontColor
        cardBackgroundColor = other.cardBackgroundColor
    }
}

@Composable
fun HydralarmTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val customColors = if (darkTheme) {
        CustomDarkColorPalette
    } else {
        CustomLightColorPalette
    }

    val systemColors = if (darkTheme) {
        SystemDarkColorPalette
    } else {
        SystemLightColorPalette
    }

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = systemColors.primaryVariant
        )
    }

    ProvideHydralarmColors(colors = customColors) {
        MaterialTheme(
            colors = systemColors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

object HydralarmTheme {
    val colors: HydralarmColors
        @Composable
        get() = LocalHydralarmColors.current
}

@Composable
fun ProvideHydralarmColors(
    colors: HydralarmColors,
    content: @Composable () -> Unit
) {
    val colorPalette = remember { colors }
    colorPalette.update(colors)
    CompositionLocalProvider(LocalHydralarmColors provides colorPalette, content = content)
}

private val LocalHydralarmColors = staticCompositionLocalOf<HydralarmColors> {
    error("No HydralarmColorPalette provided")
}
