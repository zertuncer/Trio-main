package app.trio.ui.shared

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.trio.ui.theme.TrioColor
import app.trio.ui.theme.TrioColors

/**
 * Snooze süresi (NotificationResponseAction enum karşılığı)
 */
enum class SnoozeDuration(val displayName: String) {
    SNOOZE_1("1 Hour"),
    SNOOZE_2("2 Hours"),
    SNOOZE_4("4 Hours"),
    SNOOZE_6("6 Hours"),
    SNOOZE_8("8 Hours")
}

/**
 * Orijinal iOS SnoozeAlertsSheetView eşdeğeri.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SnoozeAlertsSheetView(
    onApplySnooze: (SnoozeDuration?) -> Unit,
    onClose: () -> Unit,
    activeSnooze: SnoozeDuration? = null
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Snooze Alerts") },
                actions = {
                    IconButton(onClick = onClose) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
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
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(SnoozeDuration.values()) { duration ->
                Surface(
                    color = TrioColor.chart,
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onApplySnooze(duration)
                        onClose()
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = duration.displayName,
                            color = TrioColor.systemBlue
                        )
                    }
                }
            }

            // Aktif snooze varsa iptal etme butonu (SwipeToDelete yerine şimdilik buton)
            if (activeSnooze != null) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Surface(
                        color = TrioColor.chart,
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            onApplySnooze(null) // End snooze
                            onClose()
                        }
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "End Snooze",
                                color = TrioColors.SystemRed
                            )
                        }
                    }
                }
            }
        }
    }
}
