package app.trio.ui.home.chart.elements

import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.patrykandpatrick.vico.core.chart.decoration.Decoration
import com.patrykandpatrick.vico.core.chart.draw.ChartDrawContext
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.entry.FloatEntry
import app.trio.ui.theme.TrioColor

/**
 * iOS: ForecastView.swift (92 satır)
 * Vico'da tahmin konisi ve medyan çizgisini çizen Custom Decoration.
 */
class ForecastConeDecoration(
    private val minEntries: List<FloatEntry>,
    private val maxEntries: List<FloatEntry>,
    private val model: ChartEntryModel
) : Decoration {
    override fun onDrawBehindChart(
        context: ChartDrawContext,
        bounds: RectF
    ) {
        val canvas = context.canvas
        val paint = Paint().apply {
            color = Color(0xFF007AFF).copy(alpha = 0.2f).toArgb()
            style = Paint.Style.FILL
        }

        val minX = 20f 
        val maxX = 28f 
        val minY = 0f
        val maxY = 140f
        
        val xRange = maxX - minX
        val yRange = maxY - minY

        if (xRange == 0f || yRange == 0f || minEntries.isEmpty() || maxEntries.isEmpty()) return

        val width = bounds.width()
        val height = bounds.height()

        fun xToPixel(x: Float): Float = bounds.left + ((x - minX) / xRange) * width
        fun yToPixel(y: Float): Float = bounds.bottom - ((y - minY) / yRange) * height

        val path = Path()

        path.moveTo(xToPixel(maxEntries.first().x), yToPixel(maxEntries.first().y))
        for (i in 1 until maxEntries.size) {
            val entry = maxEntries[i]
            path.lineTo(xToPixel(entry.x), yToPixel(entry.y))
        }

        for (i in minEntries.indices.reversed()) {
            val entry = minEntries[i]
            path.lineTo(xToPixel(entry.x), yToPixel(entry.y))
        }

        path.close()
        canvas.drawPath(path, paint)
    }
}
