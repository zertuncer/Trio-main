package app.trio.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.trio.ui.theme.TrioColor

@Composable
fun PumpView(
    batteryPct: Int? = null,
    reservoirUnits: Double? = null,
    isPumpConnected: Boolean = false
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(10.dp)
    ) {
        if (!isPumpConnected) {
            Text(
                text = "Add Pump",
                style = MaterialTheme.typography.labelLarge,
                color = TrioColor.systemBlue
            )
        } else {
            Text(
                text = "${batteryPct ?: 0}%",
                style = MaterialTheme.typography.bodySmall,
                color = TrioColor.systemGreen
            )
            Text(
                text = "${reservoirUnits ?: 0.0} U",
                style = MaterialTheme.typography.bodySmall,
                color = TrioColor.systemBlue
            )
        }
    }
}
