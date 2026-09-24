package app.trio.ui.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.trio.ui.theme.TrioColor

/**
 * Orijinal iOS DosingModeOverrideNote eşdeğeri.
 */
@Composable
fun DosingModeOverrideNote(
    modeDisplayName: String,
    modeIcon: ImageVector,
    message: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = modeIcon,
            contentDescription = null,
            tint = TrioColor.secondarySystemBackground // .secondary
        )
        Text(
            text = "$modeDisplayName is on. $message",
            fontSize = 12.sp, // .footnote
            color = TrioColor.secondarySystemBackground // .secondary
        )
    }
}
