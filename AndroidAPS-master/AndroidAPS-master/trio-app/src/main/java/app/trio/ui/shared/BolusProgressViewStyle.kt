package app.trio.ui.shared

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import app.trio.ui.theme.TrioColors

/**
 * Orijinal iOS BolusProgressViewStyle eşdeğeri.
 */
@Composable
fun BolusProgressViewStyle(
    fractionCompleted: Float,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.size(30.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(22.dp)) {
            // Dış halka (arka)
            drawCircle(
                color = Color.Gray.copy(alpha = 0.3f), // .secondary eşdeğeri
                style = Stroke(width = 4.dp.toPx())
            )
            
            // İç kare
            drawRect(
                color = TrioColors.Insulin,
                topLeft = Offset(
                    (size.width - 8.dp.toPx()) / 2,
                    (size.height - 8.dp.toPx()) / 2
                ),
                size = Size(8.dp.toPx(), 8.dp.toPx())
            )

            // Progress arc (fractionCompleted)
            // SwiftUI'da -90 derece rotasyonla başlıyordu (saat 12 yönü), sweep angle = 360 * fraction
            drawArc(
                color = TrioColors.Insulin,
                startAngle = -90f,
                sweepAngle = 360f * fractionCompleted,
                useCenter = false,
                style = Stroke(
                    width = 4.dp.toPx(),
                    cap = StrokeCap.Butt,
                    join = StrokeJoin.Round
                )
            )
        }
    }
}
