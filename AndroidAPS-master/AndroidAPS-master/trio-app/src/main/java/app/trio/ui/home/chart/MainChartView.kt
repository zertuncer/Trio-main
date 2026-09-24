package app.trio.ui.home.chart

import android.graphics.RectF
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.line.lineSpec
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.chart.decoration.Decoration
import com.patrykandpatrick.vico.core.chart.draw.ChartDrawContext
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryModelOf
import app.trio.ui.theme.TrioColor
import com.patrykandpatrick.vico.core.marker.MarkerVisibilityChangeListener
import com.patrykandpatrick.vico.core.marker.Marker

@Composable
fun MainChartView(
    modifier: Modifier = Modifier,
    onMarkerVisibilityChanged: (Boolean) -> Unit = {},
    onMarkerPositionChanged: (x: Float, y: Float, isReal: Boolean) -> Unit = { _, _, _ -> }
) {
    // Seri 1 ve Seri 2'yi Vico'nun X ekseni uyumsuzluğu (boş ekran) nedeniyle
    // tek ve kesintisiz bir seride birleştiriyoruz.
    // Sadece gerçek geçmiş/şimdiki zaman verilerini tutuyoruz. (Gelecek zaman mock verileri kaldırıldı)
    val combinedEntries = listOf(
        FloatEntry(20f, 120f),
        FloatEntry(21f, 135f),
        FloatEntry(22f, 140f),
        FloatEntry(23f, 110f),
        FloatEntry(24f, 95f)
    )

    // Cone Bant - Alt Sınır (yMin) - Gelecek Zaman için
    val forecastMin = listOf(
        FloatEntry(24f, 95f),
        FloatEntry(25f, 75f),
        FloatEntry(26f, 80f),
        FloatEntry(27f, 85f),
        FloatEntry(28f, 90f)
    )

    // Cone Bant - Üst Sınır (yMax) - Gelecek Zaman için
    val forecastMax = listOf(
        FloatEntry(24f, 95f),
        FloatEntry(25f, 95f),
        FloatEntry(26f, 96f),
        FloatEntry(27f, 99f),
        FloatEntry(28f, 110f)
    )

    val chartEntryModel = entryModelOf(combinedEntries)

    val bottomAxisFormatter = AxisValueFormatter<AxisPosition.Horizontal.Bottom> { value, _ ->
        val hour = (value.toInt() % 24).toString().padStart(2, '0')
        "$hour:00"
    }

    // Decoration (Overlay Bant - Cone)
    val coneDecoration = remember(forecastMin, forecastMax, chartEntryModel) {
        ForecastConeDecoration(
            minEntries = forecastMin,
            maxEntries = forecastMax,
            model = chartEntryModel
        )
    }

    val lastRealX = combinedEntries.lastOrNull()?.x ?: 0f

    val marker = rememberTrioChartMarker(onPositionChanged = { x, y -> 
        onMarkerPositionChanged(x, y, x <= lastRealX)
    })
    
    val markerVisibilityChangeListener = remember(onMarkerVisibilityChanged) {
        object : MarkerVisibilityChangeListener {
            override fun onMarkerVisibilityChanged(isVisible: Boolean, marker: Marker) {
                onMarkerVisibilityChanged(isVisible)
            }
        }
    }

    Column(modifier = modifier) {
        Chart(
            chart = lineChart(
                lines = listOf(
                    lineSpec(
                        lineColor = androidx.compose.ui.graphics.Color.Green,
                        lineBackgroundShader = null
                    )
                ),
                axisValuesOverrider = com.patrykandpatrick.vico.core.chart.values.AxisValuesOverrider.fixed(minX = 20f, maxX = 28f, minY = 0f, maxY = 140f),
                decorations = listOf(coneDecoration)
            ),
            model = chartEntryModel,
            startAxis = rememberStartAxis(),
            bottomAxis = rememberBottomAxis(valueFormatter = bottomAxisFormatter),
            marker = marker,
            markerVisibilityChangeListener = markerVisibilityChangeListener,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )
    }
}

/**
 * Hibrit Yaklaşım: Vico'nun standart çizim özellikleri yetersiz kaldığında
 * (gölgelendirme sorunları), Chart'ın arka planına veya üstüne yerel Canvas (Path)
 * komutlarıyla özel şekiller (Cone) çizebilmemizi sağlar.
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
        val paint = android.graphics.Paint().apply {
            // Trio'nun tahmin koridoru rengi (mavi ve %20 saydam)
            color = Color(0xFF007AFF).copy(alpha = 0.2f).toArgb()
            style = android.graphics.Paint.Style.FILL
        }

        // model.minX/maxX yerine grafik ekseninin gerçek değerlerini kullanıyoruz
        // çünkü model (gerçek veriler) 24f'te bitiyor, ama eksen 28f'e kadar uzanıyor.
        val minX = 20f 
        val maxX = 28f 
        val minY = 0f
        val maxY = 140f
        
        val xRange = maxX - minX
        val yRange = maxY - minY

        // Bölme hatasını önlemek için güvenlik kontrolü
        if (xRange == 0f || yRange == 0f || minEntries.isEmpty() || maxEntries.isEmpty()) return

        val width = bounds.width()
        val height = bounds.height()

        // Değer koordinatlarını piksel koordinatlarına dönüştür
        fun xToPixel(x: Float): Float = bounds.left + ((x - minX) / xRange) * width
        fun yToPixel(y: Float): Float = bounds.bottom - ((y - minY) / yRange) * height

        val path = android.graphics.Path()

        // Üst sınırı çiz (Soldan sağa)
        path.moveTo(xToPixel(maxEntries.first().x), yToPixel(maxEntries.first().y))
        for (i in 1 until maxEntries.size) {
            val entry = maxEntries[i]
            path.lineTo(xToPixel(entry.x), yToPixel(entry.y))
        }

        // Alt sınırı çiz (Sağdan sola - geriye doğru dönerek alanı kapat)
        for (i in minEntries.indices.reversed()) {
            val entry = minEntries[i]
            path.lineTo(xToPixel(entry.x), yToPixel(entry.y))
        }

        path.close()
        canvas.drawPath(path, paint)
    }
}
