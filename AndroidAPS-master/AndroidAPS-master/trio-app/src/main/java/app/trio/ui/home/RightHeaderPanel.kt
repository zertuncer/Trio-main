package app.trio.ui.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import app.trio.ui.theme.TrioColor

@Composable
fun RightHeaderPanel() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // LoopView Placeholder
        LoopView()
        
        // Eventual BG Placeholder (Trio iOS line 170)
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("➡️", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(end = 4.dp)) // arrow.right.circle
            Text(
                text = "105", // eventualGlucose.description
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun LoopView() {
    // Shows loop status inside a Capsule, with a ring color based on freshness
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(CircleShape)
            .border(2.dp, TrioColor.systemGreen.copy(alpha = 0.4f), CircleShape)
            .padding(vertical = 5.dp, horizontal = 10.dp)
    ) {
        Text("🔁", modifier = Modifier.padding(end = 4.dp)) // Placeholder for loop ring
        Text(
            text = "3 min", // Formatter string for last loop date
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = TrioColor.systemGreen
        )
    }
}
