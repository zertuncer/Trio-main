package app.trio.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.trio.ui.theme.TrioColor
import java.math.BigDecimal

/**
 * Orijinal iOS ManualGlucoseEntryView eşdeğeri.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManualGlucoseEntryView(
    units: String, // "mg/dL" veya "mmol/L"
    onSave: (BigDecimal) -> Unit,
    onClose: () -> Unit
) {
    var amount by remember { mutableStateOf(BigDecimal.ZERO) }

    val limitLow = if (units == "mg/dL") BigDecimal("14") else BigDecimal("0.77") // 14.asMmolL yaklaşık 0.77
    val limitHigh = if (units == "mg/dL") BigDecimal("720") else BigDecimal("40.0") // 720.asMmolL yaklaşık 40.0

    val isAmountValid = amount >= limitLow && amount <= limitHigh

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Glucose") },
                navigationIcon = {
                    TextButton(onClick = onClose) {
                        Text("Close")
                    }
                }
            )
        },
        containerColor = TrioColor.secondarySystemGroupedBackground // trioBackgroundColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Section 1: Entry
            Surface(
                color = TrioColor.chart,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("New Glucose", modifier = Modifier.weight(1f))
                    // TODO: TextFieldWithToolBar onaylandığında (Madde 2 sonrası) eklenecektir.
                    Text(
                        text = "[$amount $units]",
                        color = Color.Gray
                    )
                }
            }

            // Section 2: Save Button
            Surface(
                color = if (isAmountValid) TrioColor.systemBlue else TrioColor.systemGray4,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if (isAmountValid) {
                        onSave(amount)
                        onClose()
                    }
                },
                enabled = isAmountValid
            ) {
                Box(
                    modifier = Modifier.padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Save", color = Color.White)
                }
            }
        }
    }
}
