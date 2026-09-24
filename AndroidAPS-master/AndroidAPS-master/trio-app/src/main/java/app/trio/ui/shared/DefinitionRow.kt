package app.trio.ui.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.trio.ui.theme.TrioColor

/**
 * Orijinal iOS DefinitionRow eşdeğeri.
 */
@Composable
fun DefinitionRow(
    term: String,
    iconVector: ImageVector? = null,
    color: Color? = null,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium, // fontSize ?? .subheadline
    shouldRotateIcon: Boolean = false,
    modifier: Modifier = Modifier,
    definition: @Composable () -> Unit
) {
    Column(
        modifier = modifier.padding(vertical = 5.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.padding(bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (color != null) {
                if (iconVector != null) {
                    Icon(
                        imageVector = iconVector,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.rotate(if (shouldRotateIcon) 180f else 0f)
                    )
                } else {
                    // Varsayılan daire (circle.fill)
                    // Canvas ile basit bir daire çizilebilir veya mevcut bir icon kullanılabilir.
                    androidx.compose.foundation.Canvas(modifier = Modifier.padding(2.dp).size(12.dp)) {
                        drawCircle(color = color)
                    }
                }
            }
            Text(
                text = term,
                style = textStyle,
                fontWeight = FontWeight.SemiBold
            )
        }
        
        // Definition içerik (alt metin)
        // SwiftUI'da font ve foregroundColor cascade ediliyordu. 
        // Compose'da MaterialTheme.typography ve LocalContentColor ile sağlanabilir.
        androidx.compose.runtime.CompositionLocalProvider(
            androidx.compose.material3.LocalContentColor provides TrioColor.secondarySystemBackground, // .secondary eşdeğeri
            androidx.compose.material3.LocalTextStyle provides textStyle
        ) {
            definition()
        }
    }
}
