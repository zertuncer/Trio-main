package app.trio.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density

private val DarkColorScheme = darkColorScheme(
    primary = TrioColors.SystemBlue,
    background = TrioColors.BackgroundDarkBlue
)

private val LightColorScheme = lightColorScheme(
    primary = TrioColors.SystemBlue,
    background = TrioColors.SecondarySystemBackgroundLight
)

@Composable
fun TrioTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    
    // Global Font Scale Capping (Dynamic Type Limit)
    // Ensures accessibility without breaking fixed dashboard slots (like HomeRootView)
    val density = LocalDensity.current
    val cappedDensity = Density(
        density = density.density,
        fontScale = density.fontScale.coerceAtMost(1.3f)
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = TrioTypography,
        content = {
            CompositionLocalProvider(
                LocalDensity provides cappedDensity,
                content = content
            )
        }
    )
}
