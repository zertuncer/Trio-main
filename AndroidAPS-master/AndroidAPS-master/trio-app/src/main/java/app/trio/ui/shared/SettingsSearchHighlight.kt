package app.trio.ui.shared

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import app.trio.ui.theme.TrioColor
import kotlinx.coroutines.delay

/**
 * Orijinal iOS SettingsSearchHighlight (ViewModifier) eşdeğeri.
 */
@Composable
fun Modifier.settingsSearchHighlight(
    isActive: Boolean,
    onHighlightComplete: () -> Unit
): Modifier {
    var isHighlighted by remember { mutableStateOf(false) }

    // Flash efekti (0 -> 0.6 -> 0 alpha)
    val highlightColor by animateColorAsState(
        targetValue = if (isHighlighted) TrioColor.systemBlue.copy(alpha = 0.6f) else Color.Transparent,
        animationSpec = tween(durationMillis = 1200), // easeOut(duration: 1.2)
        label = "HighlightAnim"
    )

    LaunchedEffect(isActive) {
        if (isActive) {
            delay(500) // SwiftUI'daki 0.5s delay
            isHighlighted = true
            delay(1200) // Animasyon süresi
            isHighlighted = false
            onHighlightComplete()
        }
    }

    return this.background(highlightColor)
}
