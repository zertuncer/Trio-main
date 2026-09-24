package app.trio.ui.home.chart.elements

import android.graphics.Paint
import android.graphics.RectF
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.patrykandpatrick.vico.core.chart.decoration.Decoration
import com.patrykandpatrick.vico.core.chart.draw.ChartDrawContext
import app.trio.ui.theme.TrioColor

/**
 * iOS: TempTargets.swift (56 satır)
 * Grafik üzerinde geçici hedef (Temp Target) dönemlerini gölgeli yatay alanlar olarak gösterir.
 */
class TempTargetDecoration(
    private val startX: Float,
    private val endX: Float,
    private val minTargetY: Float,
    private val maxTargetY: Float
) : Decoration {
    override fun onDrawBehindChart(
        context: ChartDrawContext,
        bounds: RectF
    ) {
        val canvas = context.canvas
        val paint = Paint().apply {
            color = Color(0xFF34C759).copy(alpha = 0.1f).toArgb() // systemGreen
            style = Paint.Style.FILL
        }
        
        val minX = 20f 
        val maxX = 28f 
        val minY = 0f
        val maxY = 140f
        
        val xRange = maxX - minX
        val yRange = maxY - minY

        if (xRange == 0f || yRange == 0f || startX >= endX) return

        val width = bounds.width()
        val height = bounds.height()
        
        fun xToPixel(x: Float): Float = bounds.left + ((x - minX) / xRange) * width
        fun yToPixel(y: Float): Float = bounds.bottom - ((y - minY) / yRange) * height

        val startPixelX = xToPixel(startX).coerceAtLeast(bounds.left)
        val endPixelX = xToPixel(endX).coerceAtMost(bounds.right)
        val topPixelY = yToPixel(maxTargetY).coerceAtLeast(bounds.top)
        val bottomPixelY = yToPixel(minTargetY).coerceAtMost(bounds.bottom)
        
        if (startPixelX < endPixelX && topPixelY < bottomPixelY) {
            canvas.drawRect(startPixelX, topPixelY, endPixelX, bottomPixelY, paint)
        }
    }
}
