package app.trio.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme

@Composable
fun GlucoseView() {
    // Şimdilik varsayılan (örnek) State verileriyle Trio UI klonu
    val cgmAvailable: Boolean = false

    if (cgmAvailable) {
        // BobbleAndTag (Gelecekte burası dairesel glikoz animasyonunu içerecek)
        Box(
            modifier = Modifier.size(100.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Glucose Bobble", style = MaterialTheme.typography.bodySmall)
        }
    } else {
        // "Add CGM" State
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("📡", style = MaterialTheme.typography.bodyLarge) // Placeholder for sensor.tag.radiowaves.forward.fill
            Text(
                text = "Add CGM",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
