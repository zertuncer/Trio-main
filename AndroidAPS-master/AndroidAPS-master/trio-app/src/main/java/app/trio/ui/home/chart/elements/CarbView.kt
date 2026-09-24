package app.trio.ui.home.chart.elements

import com.patrykandpatrick.vico.compose.chart.line.lineSpec
import com.patrykandpatrick.vico.core.chart.line.LineChart
import androidx.compose.runtime.Composable
import app.trio.ui.theme.TrioColor
import com.patrykandpatrick.vico.compose.component.shape.shader.color
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders

/**
 * iOS: CarbView.swift (69 satır)
 * Vico'da karbonhidrat verilerini grafik üzerinde nokta (scatter) veya lineSpec olarak göstermek için kullanılır.
 */
@Composable
fun rememberCarbLineSpec(): LineChart.LineSpec {
    return lineSpec(
        lineColor = TrioColor.systemOrange,
        lineBackgroundShader = DynamicShaders.color(TrioColor.systemOrange.copy(alpha = 0.2f)),
        lineThickness = androidx.compose.ui.unit.dp(0f) // Çizgi görünmez, sadece noktalar veya shader görünsün (Scatter efekti için Point ayarlaması yapılabilir)
    )
}
