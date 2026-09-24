package app.trio.ui.shared

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.trio.ui.theme.TrioColors

/**
 * Orijinal iOS BluetoothRequiredView eşdeğeri.
 */
@Composable
fun BluetoothRequiredView(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                // PF-05: iOS Ayarları yerine Android BT ayarları
                val intent = Intent(Settings.ACTION_BLUETOOTH_SETTINGS)
                context.startActivity(intent)
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Surface(
            shape = CircleShape, // Capsule eşdeğeri
            border = BorderStroke(2.dp, TrioColors.SystemRed.copy(alpha = 0.75f)),
            color = Color.Transparent,
            modifier = Modifier.padding(vertical = 6.dp, horizontal = 12.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Bluetooth, // logo.bluetooth eşdeğeri
                    contentDescription = null,
                    tint = TrioColors.SystemRed
                )
                Text(
                    text = "Bluetooth Required",
                    fontSize = 17.sp, // headline
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Text(
            text = "Tap to Enable Bluetooth in Android Settings",
            fontSize = 15.sp, // subheadline
            fontWeight = FontWeight.Bold,
            color = Color.Black.copy(alpha = 0.8f) // primary.opacity(0.8)
        )
    }
}
