package app.trio.ui.home.chart.elements

import com.patrykandpatrick.vico.compose.chart.line.lineSpec
import com.patrykandpatrick.vico.core.chart.line.LineChart
import androidx.compose.runtime.Composable
import app.trio.ui.theme.TrioColor
import com.patrykandpatrick.vico.compose.component.shape.shader.color
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders

/**
 * iOS: BasalChart.swift (330 satır)
 * Vico'da bazal profili (genellikle basamaklı çizgi) çizmek için kullanılır.
 */
@Composable
fun rememberBasalLineSpec(): LineChart.LineSpec {
    return lineSpec(
        lineColor = TrioColor.systemIndigo,
        lineBackgroundShader = DynamicShaders.color(TrioColor.systemIndigo.copy(alpha = 0.2f)),
        lineThickness = androidx.compose.ui.unit.dp(1.5f)
    )
}
