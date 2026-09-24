package app.trio.ui.shared

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.trio.ui.theme.TrioColor
import app.trio.ui.theme.TrioColors
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Action sheet içindeki tek bir satır/buton
 */
data class GlassSheetAction(
    val title: String,
    val isDestructive: Boolean = false,
    val handler: () -> Unit
)

/**
 * Orijinal iOS GlassActionSheet eşdeğeri.
 * Floating (havada asılı) panel mimarisi kullanır.
 * 
 * PF-03 Kararı: blur kullanılmaz, GlassPanelBackground altyapısına uygun
 * olarak Material3 TonalElevation/CompositeColor benzeri sade tasarım sunar.
 */
@Composable
fun GlassActionSheet(
    title: String? = null,
    message: String? = null,
    isPresented: Boolean,
    actions: List<GlassSheetAction>,
    onCancel: () -> Unit,
    onDismissRequest: () -> Unit // Sheet'i kapatmak için tetiklenir
) {
    val coroutineScope = rememberCoroutineScope()
    // Erişilebilirlik reduceTransparency varsayımı (şimdilik manuel, cihazdan okunabilir)
    val reduceTransparency = false 
    val dimmedOpacity = if (reduceTransparency) 0.4f else 0.25f

    // Popup arka planı ve panelin slide animasyonu için ZStack(Box)
    if (isPresented) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            // Arka plan (Dimmer)
            AnimatedVisibility(
                visible = isPresented,
                enter = fadeIn(animationSpec = tween(280)),
                exit = fadeOut(animationSpec = tween(220))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = dimmedOpacity))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            coroutineScope.launch {
                                // dismiss animasyonu için gecikme (onCancel çağrısı arkadan gelir)
                                onDismissRequest()
                                delay(240) // SwiftUI'daki 0.24s delay
                                onCancel()
                            }
                        }
                )
            }

            // Actions ve Cancel Panel (Slide-in)
            AnimatedVisibility(
                visible = isPresented,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = spring(dampingRatio = 0.8f, stiffness = 400f) // .snappy(0.28)
                ),
                exit = slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = spring(dampingRatio = 0.8f, stiffness = 400f) // .snappy(0.22)
                ),
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(bottom = 6.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Actions Panel
                    GlassPanelBackground(
                        tint = null, // GlassActionSheet orijinalinde tint almaz, saf glassEffect alır.
                        strokeWidth = 0f
                    ) {
                        Column {
                            if (title != null || message != null) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 14.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    if (title != null) {
                                        Text(
                                            text = title,
                                            fontSize = 13.sp, // footnote
                                            fontWeight = FontWeight.SemiBold,
                                            color = TrioColor.secondarySystemBackground, // .secondary
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                    if (message != null) {
                                        Text(
                                            text = message,
                                            fontSize = 13.sp, // footnote
                                            color = TrioColor.secondarySystemBackground, // .secondary
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                                Divider(color = TrioColor.systemGray4)
                            }

                            actions.forEachIndexed { index, action ->
                                if (index > 0) {
                                    Divider(color = TrioColor.systemGray4)
                                }
                                TextButton(
                                    onClick = {
                                        coroutineScope.launch {
                                            onDismissRequest()
                                            delay(240)
                                            action.handler()
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .defaultMinSize(minHeight = 57.dp)
                                ) {
                                    Text(
                                        text = action.title,
                                        fontSize = 20.sp, // title3
                                        color = if (action.isDestructive) TrioColors.SystemRed else TrioColor.systemBlue
                                    )
                                }
                            }
                        }
                    }

                    // Cancel Panel
                    GlassPanelBackground(
                        tint = null,
                        strokeWidth = 0f
                    ) {
                        TextButton(
                            onClick = {
                                coroutineScope.launch {
                                    onDismissRequest()
                                    delay(240)
                                    onCancel()
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .defaultMinSize(minHeight = 57.dp)
                        ) {
                            Text(
                                text = "Cancel",
                                fontSize = 20.sp, // title3
                                fontWeight = FontWeight.SemiBold,
                                color = TrioColor.systemBlue
                            )
                        }
                    }
                }
            }
        }
    }
}
