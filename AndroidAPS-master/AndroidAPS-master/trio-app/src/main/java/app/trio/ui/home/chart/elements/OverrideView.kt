package app.trio.ui.home.chart.elements

import android.graphics.Paint
import android.graphics.RectF
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.patrykandpatrick.vico.core.chart.decoration.Decoration
import com.patrykandpatrick.vico.core.chart.draw.ChartDrawContext
import app.trio.ui.theme.TrioColor

/**
 * iOS: OverrideView.swift (75 satır)
 * Grafik üzerinde geçersiz kılma (override) dönemlerini arka planda gölgeli alan (band) olarak gösterir.
 */
class OverrideDecoration(
    private val startX: Float,
    private val endX: Float
) : Decoration {
    override fun onDrawBehindChart(
        context: ChartDrawContext,
        bounds: RectF
    ) {
        val canvas = context.canvas
        val paint = Paint().apply {
            color = Color(0xFFFF9500).copy(alpha = 0.1f).toArgb() // systemOrange
            style = Paint.Style.FILL
        }
        
        val minX = 20f // Mock limits
        val maxX = 28f 
        val xRange = maxX - minX

        if (xRange == 0f || startX >= endX) return

        val width = bounds.width()
        
        fun xToPixel(x: Float): Float = bounds.left + ((x - minX) / xRange) * width

        val startPixel = xToPixel(startX).coerceAtLeast(bounds.left)
        val endPixel = xToPixel(endX).coerceAtMost(bounds.right)
        
        if (startPixel < endPixel) {
            canvas.drawRect(startPixel, bounds.top, endPixel, bounds.bottom, paint)
        }
    }
}
