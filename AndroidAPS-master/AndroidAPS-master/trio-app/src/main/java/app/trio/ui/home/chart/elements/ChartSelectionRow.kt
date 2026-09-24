package app.trio.ui.home.chart.elements

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.trio.ui.theme.TrioColor

/**
 * iOS: ChartSelectionRow.swift (211 satır)
 * Grafik üzerinde pan (scrub) yapıldığında gösterilen seçili değerlerin yatay bandı
 */
@Composable
fun ChartSelectionRow(
    selectedTime: String,
    selectedValue: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = selectedTime, style = MaterialTheme.typography.bodyMedium, color = TrioColor.secondaryLabel)
        Text(text = selectedValue, style = MaterialTheme.typography.bodyMedium, color = TrioColor.systemBlue)
    }
}
