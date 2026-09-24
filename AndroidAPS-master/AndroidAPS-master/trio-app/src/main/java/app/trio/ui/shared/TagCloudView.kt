package app.trio.ui.shared

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.trio.ui.theme.TrioColors
import java.util.regex.Pattern

/**
 * Orijinal iOS TagCloudView eşdeğeri.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagCloudView(
    tags: List<String>,
    shouldParseToMmolL: Boolean,
    modifier: Modifier = Modifier
) {
    // Android/Compose'da ZStack + alignmentGuide ile karmaşık hesaplamalar yapmak yerine,
    // FlowRow tam olarak bu line-breaking akışını sağlar.
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        tags.forEach { tag ->
            val formattedTag = formatGlucoseTags(tag, shouldParseToMmolL)
            val tagColor = getColorOfTag(tag)

            Surface(
                shape = CircleShape,
                color = tagColor.copy(alpha = 0.15f), // iOS (0.15 dark, 0.25 light)
                border = BorderStroke(2.dp, tagColor.copy(alpha = 0.4f)),
            ) {
                Text(
                    text = formattedTag,
                    style = MaterialTheme.typography.bodyMedium, // subheadline
                    fontWeight = FontWeight.SemiBold,
                    color = tagColor,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                )
            }
        }
    }
}

/**
 * Renk kodlama kuralları
 */
private fun getColorOfTag(textTag: String): Color {
    return when {
        textTag.contains("SMB Delivery Ratio:") -> TrioColors.UAM
        textTag.contains("Bolus") || textTag.contains("Swift Oref") -> TrioColors.SystemGreen
        textTag.contains("TDD:") || textTag.contains("tdd_factor") || 
        textTag.contains("Sigmoid function") || textTag.contains("Logarithmic formula") ||
        textTag.contains("AF:") || textTag.contains("Autosens/Dynamic Limit:") ||
        textTag.contains("Dynamic ISF/CR") || textTag.contains("Basal ratio") || 
        textTag.contains("SMB Ratio") && !textTag.contains("SMB Ratio") /* Orj. Bug? Orjinalde SMB Ratio ZT döner, ama altta Orange var */ -> TrioColors.ZT
        textTag.contains("Middleware:") -> TrioColors.SystemRed
        textTag.contains("SMB Ratio") -> TrioColors.SystemOrange
        textTag.contains("Smoothing: On") -> TrioColors.SystemGray4Light
        else -> TrioColors.Insulin
    }
}

/**
 * mg/dL -> mmol/L dönüşüm Regex rutini (iOS birebir)
 */
private fun formatGlucoseTags(tag: String, isMmolL: Boolean): String {
    if (!isMmolL) return tag // isMmolL false ise hiç değiştirme

    val patterns = listOf(
        "(?:ISF|Target):\\s*-?\\d+\\.?\\d*(?:→-?\\d+\\.?\\d*)+",
        "Dev:\\s*-?\\d+\\.?\\d*",
        "BGI:\\s*-?\\d+\\.?\\d*",
        "Target:\\s*-?\\d+\\.?\\d*",
        "(?:minPredBG|minGuardBG|IOBpredBG|COBpredBG|UAMpredBG)\\s*-?\\d+\\.?\\d*"
    )
    val pattern = patterns.joinToString("|")
    val regex = Pattern.compile(pattern)
    val matcher = regex.matcher(tag)

    val stringBuilder = StringBuffer()

    while (matcher.find()) {
        val matchString = matcher.group()
        
        // TODO: convertToMmolL(matchString) işlevi gerçek mmoll dönüşümünü yapacak. 
        // Şimdilik string manüpülasyonu stublanmıştır.
        val replacedString = matchString // Asıl dönüşüm algoritmik implementasyonu sonraki görevlere bağlanacak
        
        matcher.appendReplacement(stringBuilder, replacedString)
    }
    matcher.appendTail(stringBuilder)
    
    return stringBuilder.toString()
}
