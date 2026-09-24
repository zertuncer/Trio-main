package app.trio.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import app.trio.ui.theme.TrioColor

@Composable
fun BottomControls() {
    Column(
        verticalArrangement = Arrangement.spacedBy(HomeLayout.bottomZonePadding),
        modifier = Modifier.padding(vertical = HomeLayout.bottomZonePadding)
    ) {
        // AdjustmentView / Bolus Progress (Trio iOS line 818)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(HomeLayout.bottomPanelHeight)
                .padding(horizontal = 10.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(TrioColor.tertiaryLabel.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No Active Adjustment\nProfile at 100 %",
                style = MaterialTheme.typography.bodySmall,
                color = TrioColor.tertiaryLabel
            )
        }
        
        // MultiUsePanel / StatsBanner (Trio iOS line 824)
        // Replaced placeholder with an actual Stacked Bar Chart mapping GlucoseDailyDistributionStats
        TirStatsBanner(
            inRangePct = 75.0, // Mocked for now, will connect to ChartDataMapper in Phase 3
            inSmallRangePct = 60.0,
            highPct = 15.0,
            veryHighPct = 5.0,
            lowPct = 3.0,
            veryLowPct = 2.0
        )
    }
}

@Composable
fun TirStatsBanner(
    inRangePct: Double,
    inSmallRangePct: Double,
    highPct: Double,
    veryHighPct: Double,
    lowPct: Double,
    veryLowPct: Double
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp) // HomeLayout.statsBannerHeight
            .padding(horizontal = 10.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(TrioColor.tertiaryLabel.copy(alpha = 0.1f))
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("${inRangePct.toInt()}% Time in Range", style = MaterialTheme.typography.bodySmall)
            }
            
            // Stacked Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                if (veryLowPct > 0) Box(modifier = Modifier.weight(veryLowPct.toFloat()).fillMaxHeight().background(TrioColor.systemRed))
                if (lowPct > 0) Box(modifier = Modifier.weight(lowPct.toFloat()).fillMaxHeight().background(Color.Red.copy(alpha=0.6f)))
                if (inRangePct > 0) Box(modifier = Modifier.weight(inRangePct.toFloat()).fillMaxHeight().background(TrioColor.systemGreen))
                if (highPct > 0) Box(modifier = Modifier.weight(highPct.toFloat()).fillMaxHeight().background(Color(0xFFFFD60A))) // systemYellow fallback
                if (veryHighPct > 0) Box(modifier = Modifier.weight(veryHighPct.toFloat()).fillMaxHeight().background(Color(0xFFFF9F0A))) // systemOrange fallback
            }
        }
    }
}
