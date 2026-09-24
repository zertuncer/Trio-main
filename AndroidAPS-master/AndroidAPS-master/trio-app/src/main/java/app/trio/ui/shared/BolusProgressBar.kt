package app.trio.ui.shared

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.trio.ui.theme.TrioColors
import java.math.BigDecimal

/**
 * Orijinal iOS BolusProgressBar eşdeğeri.
 */
@Composable
fun BolusProgressBar(
    progress: BigDecimal,
    modifier: Modifier = Modifier
) {
    // Progress animasyonu (0.0 - 1.0 arası)
    val animatedProgress by animateFloatAsState(
        targetValue = progress.toFloat().coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 250), // easeInOut(duration: 0.25)
        label = "BolusProgressAnim"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(6.dp)
            .background(Color.Transparent),
        contentAlignment = Alignment.CenterStart
    ) {
        // Tam genişlik maskesi, LinearGradient ile dolgu
        Box(
            modifier = Modifier
                .fillMaxWidth(animatedProgress)
                .fillMaxHeight()
                .clip(RoundedCornerShape(15.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = TrioColors.TrioGradient
                    )
                )
        )
    }
}
