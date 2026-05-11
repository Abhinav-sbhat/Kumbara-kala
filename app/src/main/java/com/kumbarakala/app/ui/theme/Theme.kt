package com.kumbarakala.app.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val KumbaraColorScheme = lightColorScheme(
    primary = Terracotta,
    onPrimary = WarmCream,
    primaryContainer = TerracottaLight,
    onPrimaryContainer = DeepBrownDark,

    secondary = DeepBrown,
    onSecondary = WarmCream,
    secondaryContainer = SoftBeige,
    onSecondaryContainer = DeepBrown,

    tertiary = Gold,
    onTertiary = DeepBrownDark,
    tertiaryContainer = GoldLight,
    onTertiaryContainer = DeepBrown,

    background = WarmCream,
    onBackground = DarkBrown,

    surface = WarmCream,
    onSurface = DarkBrown,
    surfaceVariant = SoftBeige,
    onSurfaceVariant = DeepBrown,

    error = ErrorRed,
    onError = WarmCream,

    outline = LightBrown,
    outlineVariant = SoftBeige
)

private val KumbaraShapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

@Composable
fun KumbaraKalaTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = KumbaraColorScheme,
        typography = KumbaraTypography,
        shapes = KumbaraShapes,
        content = content
    )
}
