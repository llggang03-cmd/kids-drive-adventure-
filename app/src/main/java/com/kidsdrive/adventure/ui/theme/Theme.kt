package com.kidsdrive.adventure.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val KidsColorScheme = lightColorScheme(
    primary = PrimaryOrange,
    onPrimary = Color.White,
    secondary = AccentYellow,
    background = SoftWhite,
    surface = SoftWhite,
    onBackground = TextDark,
    onSurface = TextDark
)

@Composable
fun KidsDriveAdventureTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = KidsColorScheme,
        typography = KidsTypography,
        content = content
    )
}
