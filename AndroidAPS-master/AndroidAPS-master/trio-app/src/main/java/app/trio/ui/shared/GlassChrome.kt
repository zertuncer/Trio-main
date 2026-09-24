package app.trio.ui.shared

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.unit.dp
import app.trio.ui.theme.TrioColor

/**
 * Trio iOS GlassChrome enum eşdeğeri sabitler.
 */
object GlassChrome {
    val panelCornerRadius = 26.dp
    val panelShape = RoundedCornerShape(panelCornerRadius)
}

/**
 * GlassPanelBackground Modifier
 * PF-03 Kararı (Seçenek 1 Karma Yaklaşım): 
 * Performans için blur (Material) yerine Android TonalElevation + Tint CompositeColor kullanır.
 */
@Composable
fun GlassPanelBackground(
    modifier: Modifier = Modifier,
    tint: Color? = null,
    tintOpacity: Float = 0.12f,
    strokeOpacity: Float = 0.35f,
    strokeWidth: Float = 1f,
    content: @Composable () -> Unit
) {
    // Eğer tint varsa, tint'i surface üzerine composite (karıştırarak) boyuyoruz.
    val baseColor = MaterialTheme.colorScheme.surface
    val compositeColor = if (tint != null) {
        tint.copy(alpha = tintOpacity).compositeOver(baseColor)
    } else {
        baseColor
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = strokeWidth.dp,
                color = (tint ?: TrioColor.systemGray5).copy(alpha = strokeOpacity),
                shape = GlassChrome.panelShape
            ),
        shape = GlassChrome.panelShape,
        tonalElevation = 2.dp, // Derinlik (glass) hissi veren gölge
        color = compositeColor
    ) {
        content()
    }
}
