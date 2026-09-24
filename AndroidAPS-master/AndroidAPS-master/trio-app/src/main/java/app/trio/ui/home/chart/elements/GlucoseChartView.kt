package app.trio.ui.home.chart.elements

import com.patrykandpatrick.vico.compose.chart.line.lineSpec
import com.patrykandpatrick.vico.core.chart.line.LineChart
import androidx.compose.runtime.Composable
import app.trio.ui.theme.TrioColor

/**
 * iOS: GlucoseChartView.swift (120 satır)
 * Vico'da ana glikoz çizgisini ve noktalarını temsil eden yapı
 */
@Composable
fun rememberGlucoseLineSpec(): LineChart.LineSpec {
    return lineSpec(
        lineColor = TrioColor.systemGreen,
        lineBackgroundShader = null,
        lineThickness = androidx.compose.ui.unit.dp(2f)
    )
}
