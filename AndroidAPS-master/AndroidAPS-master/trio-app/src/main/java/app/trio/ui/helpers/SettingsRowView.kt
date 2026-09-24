package app.trio.ui.helpers

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.trio.ui.theme.TrioColor

/**
 * Orijinal iOS SettingsRowView eşdeğeri.
 */
@Composable
fun SettingsRowView(
    iconPainter: Painter,
    title: String,
    tint: Color,
    spacing: Dp = 12.dp,
    iconSize: Dp = 35.dp,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = iconPainter,
            contentDescription = null, // decorative
            tint = tint,
            modifier = Modifier.size(iconSize)
        )

        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium, // subheadline muadili
            color = TrioColor.secondarySystemBackground // TODO: Text primary color olmalı
        )
    }
}

/**
 * Orijinal iOS SettingsRowViewCustomImage eşdeğeri.
 */
@Composable
fun SettingsRowViewCustomImage(
    @DrawableRes imageRes: Int,
    title: String,
    frameSize: Dp = 35.dp,
    spacing: Dp = 12.dp,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier.size(frameSize)
        )

        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium // subheadline muadili
        )
    }
}
