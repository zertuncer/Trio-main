package app.trio.ui.home.chart.elements

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.patrykandpatrick.vico.compose.chart.line.lineSpec
import com.patrykandpatrick.vico.core.chart.decoration.Decoration
import com.patrykandpatrick.vico.core.chart.decoration.ThresholdLine
import com.patrykandpatrick.vico.compose.component.lineComponent
import androidx.compose.ui.unit.dp

/**
 * iOS: GlucoseTargetsView.swift (35 satır)
 * Vico'da Hedef aralığı (Min-Max) eşik çizgileri olarak gösterir.
 */
@Composable
fun rememberGlucoseTargetsDecorations(
    lowTarget: Float = 70f,
    highTarget: Float = 180f
): List<Decoration> {
    val lowThreshold = rememberThresholdLine(target = lowTarget, color = Color.Red)
    val highThreshold = rememberThresholdLine(target = highTarget, color = Color.Red)
    
    return listOf(lowThreshold, highThreshold)
}

@Composable
private fun rememberThresholdLine(target: Float, color: Color): ThresholdLine {
    val line = lineComponent(
        color = color,
        thickness = 1.dp
    )
    return remember(target, color) {
        ThresholdLine(
            thresholdValue = target,
            lineComponent = line
        )
    }
}
