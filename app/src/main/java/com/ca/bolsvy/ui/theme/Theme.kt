package com.ca.bolsvy.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val EsquemaClaro = lightColorScheme(
    primary = Cafe,
    onPrimary = Color.White,
    secondary = Rosa,
    onSecondary = TextoOscuro,
    tertiary = Azul,
    onTertiary = TextoOscuro,
    secondaryContainer = Lila,
    onSecondaryContainer = TextoOscuro,
    background = Crema,
    onBackground = TextoOscuro,
    surface = Color.White,
    onSurface = TextoOscuro,
    error = Rojo,
    onError = Color.White
)

@Composable
fun BolsvyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = EsquemaClaro,
        typography = Typography,
        content = content
    )
}