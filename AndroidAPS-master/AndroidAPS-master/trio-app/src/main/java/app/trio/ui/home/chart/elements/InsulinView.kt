package app.trio.ui.home.chart.elements

import com.patrykandpatrick.vico.compose.chart.line.lineSpec
import com.patrykandpatrick.vico.core.chart.line.LineChart
import androidx.compose.runtime.Composable
import app.trio.ui.theme.TrioColor
import com.patrykandpatrick.vico.compose.component.shape.shader.color
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders

/**
 * iOS: InsulinView.swift (51 satır)
 * Vico'da insülin verilerini grafik üzerinde sütun (veya point) olarak göstermek için kullanılır.
 */
@Composable
fun rememberInsulinLineSpec(): LineChart.LineSpec {
    return lineSpec(
        lineColor = TrioColor.systemTeal,
        lineBackgroundShader = DynamicShaders.color(TrioColor.systemTeal.copy(alpha = 0.2f)),
        lineThickness = androidx.compose.ui.unit.dp(0f) 
    )
}
