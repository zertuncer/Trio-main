package app.trio.ui.shared

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.trio.ui.theme.TrioColor

/**
 * Cihaz kataloğu girişi (Örnek Data)
 */
data class DeviceCatalogEntry(
    val manufacturer: String,
    val name: String,
    val identifier: String
)

/**
 * Orijinal iOS DevicePickerView eşdeğeri.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DevicePickerView(
    availableDevices: List<DeviceCatalogEntry>,
    onDeviceSelected: (DeviceCatalogEntry) -> Unit,
    onClose: () -> Unit
) {
    // Üreticiye göre grupla
    val groupedDevices = availableDevices.groupBy { it.manufacturer }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Device") },
                actions = {
                    TextButton(onClick = onClose) {
                        Text("Cancel")
                    }
                }
            )
        },
        containerColor = TrioColor.secondarySystemGroupedBackground
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            groupedDevices.forEach { (manufacturer, devices) ->
                item {
                    Text(
                        text = manufacturer,
                        style = MaterialTheme.typography.labelMedium,
                        color = TrioColor.tertiaryLabel,
                        modifier = Modifier.padding(start = 16.dp, bottom = 4.dp)
                    )
                }

                items(devices) { device ->
                    Surface(
                        color = TrioColor.chart,
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onDeviceSelected(device) }
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = device.name,
                                modifier = Modifier.weight(1f)
                            )
                            // iOS'daki Chevron
                            Text(">", color = TrioColor.tertiaryLabel)
                        }
                    }
                }
            }
        }
    }
}
