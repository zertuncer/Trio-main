package app.trio.ui.helpers

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.trio.ui.theme.TrioColor
import app.trio.ui.theme.TrioColors

/**
 * Orijinal iOS CustomProgressView eşdeğeri.
 */
@Composable
fun CustomProgressView(
    text: String,
    modifier: Modifier = Modifier
) {
    // Animasyon offset'i (-180'den 180'e döngü)
    val infiniteTransition = rememberInfiniteTransition(label = "ProgressAnimation")
    val offsetX by infiniteTransition.animateFloat(
        initialValue = -180f,
        targetValue = 180f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "OffsetX"
    )

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        // Text (y ekseninde yukarı kaydırılmış)
        Text(
            text = text,
            // PF-09 UYARISI: Rounded font yerine sistem default kullanılıyor, 
            // Madde 4 kararına göre güncellenecek.
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.offset(y = (-25).dp)
        )

        // Arka plan çubuğu (Gri)
        Box(
            modifier = Modifier
                .width(250.dp)
                .height(3.dp)
                .border(
                    width = 3.dp,
                    color = TrioColor.systemGray5,
                    shape = RoundedCornerShape(3.dp)
                )
        )

        // Gradient çubuğu ve Mask
        Box(
            modifier = Modifier
                .width(250.dp)
                .height(3.dp)
                .border(
                    width = 3.dp,
                    brush = Brush.horizontalGradient(colors = TrioColors.TrioGradient),
                    shape = RoundedCornerShape(3.dp)
                )
                // Maskeleme efekti (Android Compose'da tam eşdeğer için 
                // gradient çubuğu içine clip/offset ile kutu koyarak simüle edebiliriz)
        ) {
            // Animasyonlu maske
            Box(
                modifier = Modifier
                    .offset(x = offsetX.dp)
                    .width(80.dp)
                    .height(3.dp)
                    .background(TrioColor.secondarySystemBackground) 
                    // Not: Orijinalinde mask kullanılmış. 
                    // Compose'da maske olarak clip() kullanılabilir.
            )
        }
    }
}
