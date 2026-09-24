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
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith

@Composable
fun MealPanel(
    isChartReadoutVisible: Boolean = false,
    chartReadoutDate: Float? = null,
    chartReadoutGlucose: Float? = null
) {
    AnimatedContent(
        targetState = isChartReadoutVisible,
        transitionSpec = {
            fadeIn(animationSpec = tween(120)) togetherWith fadeOut(animationSpec = tween(120))
        },
        label = "MealPanelReadoutTransition"
    ) { showReadout ->
        if (showReadout) {
            ChartSelectionRow(chartReadoutDate, chartReadoutGlucose)
        } else {
            NormalMealPanel()
        }
    }
}

@Composable
private fun NormalMealPanel() {
    val cob: Double = 0.0
    val iob: Double = 0.0
    val isSnoozed = false
    val remainingMinutes = 0

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Center: COB (Carbs on board)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("🍽️", modifier = Modifier.padding(end = 4.dp)) // fork.knife placeholder
            Text(
                text = "${cob.toInt()} g",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }

        // Left: IOB (Insulin on board)
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("💉", modifier = Modifier.padding(end = 4.dp)) // syringe.fill placeholder
            Text(
                text = "${iob.toInt()} U",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = TrioColor.systemBlue // Color.insulin
            )
        }

        // Right: Alarms pill
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isSnoozed) {
                // Snoozed State
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(CircleShape)
                        .border(2.dp, TrioColor.tertiaryLabel.copy(alpha = 0.4f), CircleShape)
                        .padding(vertical = 5.dp, horizontal = 10.dp)
                ) {
                    Text("🔕", modifier = Modifier.padding(end = 4.dp))
                    Text(
                        text = "$remainingMinutes m",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = TrioColor.tertiaryLabel
                    )
                }
            } else {
                // Normal State
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .border(2.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🔔", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Composable
private fun ChartSelectionRow(chartReadoutDate: Float?, chartReadoutGlucose: Float?) {
    // A bar that shows the time and glucose at `chartReadoutDate`.
    // We use a mock logic for the time just for the prototype.
    val hour = chartReadoutDate?.let { (it.toInt() % 24).toString().padStart(2, '0') + ":00" } ?: "--:--"
    val glucoseDisplay = chartReadoutGlucose?.let { "${it.toInt()} mg/dL" } ?: "-- mg/dL"
    
    // In Trio iOS, the readout looks like:
    // [Date Time]  [Glucose]  [IOB] [COB]
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = hour, 
            fontWeight = FontWeight.Bold, 
            color = TrioColor.tertiaryLabel
        )
        Text(
            text = glucoseDisplay,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "0 U", 
            fontWeight = FontWeight.Bold, 
            color = TrioColor.systemBlue
        )
    }
}
