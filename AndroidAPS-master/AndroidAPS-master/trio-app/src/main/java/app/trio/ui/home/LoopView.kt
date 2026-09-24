package app.trio.ui.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.trio.ui.theme.TrioColor

@Composable
fun LoopView(
    deltaMinutes: Int, // secondsSinceLastLoop / 60
    hasDeviceIssue: Boolean,
    eventualBg: Int?
) {
    // Determine freshness color (Matches LoopView.swift 60-68)
    val ringColor: Color = when {
        hasDeviceIssue -> TrioColor.systemRed
        deltaMinutes <= 5 -> TrioColor.systemGreen
        deltaMinutes <= 10 -> Color(0xFFFFD60A) // systemYellow fallback
        else -> TrioColor.systemRed // > 10 mins = Stale
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .border(2.dp, ringColor.copy(alpha = 0.4f), RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        // Mock Icon for now
        Text(
            text = "O", // Represents loop circle
            color = ringColor
        )
        if (eventualBg != null) {
            Text(
                text = eventualBg.toString(),
                color = ringColor
            )
        }
    }
}
