package app.trio.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Orijinal iOS ViewModifiers eşdeğeri.
 */

fun Modifier.roundedBackground(
    color: Color,
    cornerRadius: Float = 8f,
    padding: Float = 10f
): Modifier = this
    .background(color, RoundedCornerShape(cornerRadius.dp))
    .padding(padding.dp)

fun Modifier.capsuleBackground(
    color: Color,
    paddingHorizontal: Float = 10f,
    paddingVertical: Float = 5f
): Modifier = this
    .background(color, CircleShape)
    .padding(horizontal = paddingHorizontal.dp, vertical = paddingVertical.dp)

// Not: NavigationLazyView, ScreenNavigation ve ClearButton (TextField parçası),
// Compose'un kendi mimarisi (NavGraph ve TextField yapısı) tarafından otomatik ele alındığı
// veya TextField içinde tanımlanacağı için burada birebir modifier olarak yer almamaktadır.
