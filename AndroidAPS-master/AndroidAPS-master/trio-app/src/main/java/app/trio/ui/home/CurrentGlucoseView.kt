package app.trio.ui.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import app.trio.ui.theme.TrioColor

@Composable
fun CurrentGlucoseView(
    glucoseValue: Int?,
    isReadingFresh: Boolean,
    progress: Double?, // cgmProgress
    color: Color = TrioColor.systemGreen
) {
    val diameter = 146.dp
    val strokeWidth = 4.dp

    Box(
        modifier = Modifier.size(diameter),
        contentAlignment = Alignment.Center
    ) {
        // Bobble Background
        Canvas(modifier = Modifier.size(124.dp)) {
            drawCircle(
                color = color.copy(alpha = 0.2f)
            )
        }

        // Sensor Lifecycle Arc (Matches SensorLifecycleArcView.swift trim math)
        if (progress != null) {
            Canvas(modifier = Modifier.size(diameter)) {
                // Faint track behind the arc
                drawCircle(
                    color = Color.Gray.copy(alpha = 0.4f),
                    style = Stroke(width = strokeWidth.toPx())
                )

                // Active Arc (Starts at -90, drains counter-clockwise)
                // trim(from: 0, to: 1 - progress) in Swift
                val sweepAngle = (1f - progress.coerceIn(0.0, 1.0).toFloat()) * -360f
                drawArc(
                    color = color,
                    startAngle = -90f,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
                )
            }
        }

        // Glucose Number
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (glucoseValue != null && isReadingFresh) {
                Text(
                    text = if (glucoseValue >= 400) "HIGH" else glucoseValue.toString(),
                    style = MaterialTheme.typography.displayMedium,
                    color = color
                )
            } else {
                Text(
                    text = "– –",
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.Gray
                )
            }
        }
    }
}
