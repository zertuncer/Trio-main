package app.trio.ui.home.chart.elements

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.trio.ui.theme.TrioColor

/**
 * iOS: ChartLegendView.swift (291 satır)
 * Grafik altındaki göstergeler (Glucose, Basal, vb.)
 */
@Composable
fun ChartLegendView(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        LegendItem("Glucose", TrioColor.systemBlue)
        LegendItem("IOB", TrioColor.systemTeal)
        LegendItem("COB", TrioColor.systemOrange)
    }
}

@Composable
private fun LegendItem(label: String, color: androidx.compose.ui.graphics.Color) {
    Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
        androidx.compose.foundation.Canvas(modifier = Modifier.size(8.dp)) {
            drawCircle(color)
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = label, style = MaterialTheme.typography.bodySmall, color = TrioColor.secondaryLabel)
    }
}
