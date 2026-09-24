package app.trio.ui.shared

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

enum class PopupDirection {
    TOP, BOTTOM
}

/**
 * Orijinal iOS PopupModifier eşdeğeri.
 */
@Composable
fun Popup(
    isPresented: Boolean,
    direction: PopupDirection = PopupDirection.BOTTOM,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    popupContent: @Composable () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        // Asıl içerik
        content()

        // Popup katmanı (overlay)
        val alignment = if (direction == PopupDirection.BOTTOM) Alignment.BottomCenter else Alignment.TopCenter
        
        AnimatedVisibility(
            visible = isPresented,
            modifier = Modifier.align(alignment),
            enter = slideInVertically(
                animationSpec = spring(dampingRatio = 0.8f, stiffness = 400f),
                initialOffsetY = { if (direction == PopupDirection.BOTTOM) it else -it }
            ),
            exit = slideOutVertically(
                animationSpec = spring(dampingRatio = 0.8f, stiffness = 400f),
                targetOffsetY = { if (direction == PopupDirection.BOTTOM) it else -it }
            )
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                popupContent()
            }
        }
    }
}
