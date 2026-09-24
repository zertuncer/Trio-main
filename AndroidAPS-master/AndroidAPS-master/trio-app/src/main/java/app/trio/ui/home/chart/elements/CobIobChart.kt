package app.trio.ui.home.chart.elements

import com.patrykandpatrick.vico.compose.chart.line.lineSpec
import com.patrykandpatrick.vico.core.chart.line.LineChart
import androidx.compose.runtime.Composable
import app.trio.ui.theme.TrioColor
import com.patrykandpatrick.vico.compose.component.shape.shader.color
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders

/**
 * iOS: CobIobChart.swift (173 satır)
 * Vico'da COB (Carbs On Board) ve IOB (Insulin On Board) çizgilerini çizer.
 */
@Composable
fun rememberCobLineSpec(): LineChart.LineSpec {
    return lineSpec(
        lineColor = TrioColor.systemOrange,
        lineBackgroundShader = DynamicShaders.color(TrioColor.systemOrange.copy(alpha = 0.1f)),
        lineThickness = androidx.compose.ui.unit.dp(1.5f)
    )
}

@Composable
fun rememberIobLineSpec(): LineChart.LineSpec {
    return lineSpec(
        lineColor = TrioColor.systemTeal,
        lineBackgroundShader = DynamicShaders.color(TrioColor.systemTeal.copy(alpha = 0.1f)),
        lineThickness = androidx.compose.ui.unit.dp(1.5f)
    )
}
