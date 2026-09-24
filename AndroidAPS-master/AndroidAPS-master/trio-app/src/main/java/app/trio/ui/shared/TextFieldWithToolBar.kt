package app.trio.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.trio.ui.theme.TrioColor
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

/**
 * Orijinal iOS TextFieldWithToolBar eşdeğeri.
 */
@Composable
fun TextFieldWithToolBar(
    value: BigDecimal,
    onValueChange: (BigDecimal) -> Unit,
    placeholder: String,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    textAlign: TextAlign = TextAlign.End,
    keyboardType: KeyboardType = KeyboardType.Decimal,
    maxValue: BigDecimal? = null,
    isDismissible: Boolean = true,
    showArrows: Boolean = false,
    onPrevious: (() -> Unit)? = null,
    onNext: (() -> Unit)? = null,
    unitsText: String? = null,
    unitsTextColor: Color = TrioColor.tertiaryLabel,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }

    // Locale-aware ayracın tespiti
    val symbols = DecimalFormatSymbols(Locale.getDefault())
    val decimalSeparator = symbols.decimalSeparator.toString()

    // TextField metin state'i
    var textValue by remember {
        mutableStateOf(if (value.compareTo(BigDecimal.ZERO) == 0) "" else value.toPlainString().replace(".", decimalSeparator))
    }
    
    // Klavyenin açık olup olmaması durumuna göre Toolbar (Compose'da ImePadding ile ekranın altına veya doğrudan TextField üstüne row olarak çizilebilir). 
    // Şimdilik sadece TextField ve yanında unit text olarak implemente edilmiştir. 
    // TODO: IME üzerinde gezen Toolbar (inputAccessoryView karşılığı) için WindowInsets.ime API'si kullanılacaktır.

    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = textValue,
                onValueChange = { newValue ->
                    // 1. Birden fazla decimal separator engelle
                    if (newValue.count { it.toString() == decimalSeparator } > 1) return@TextField

                    // 2. Virgül / nokta dönüşümü
                    var processedValue = newValue.replace(",", decimalSeparator).replace(".", decimalSeparator)

                    // 3. Leading decimal -> "0."
                    if (processedValue.startsWith(decimalSeparator)) {
                        processedValue = "0$processedValue"
                    }

                    textValue = processedValue

                    // Sadece geçerli sayılar için BigDecimal'a çevir, parse edilemezse 0'a çek (veya silinmişse)
                    if (processedValue.isEmpty()) {
                        onValueChange(BigDecimal.ZERO)
                    } else {
                        try {
                            // Java BigDecimal her zaman "." ile çalışır.
                            val parseableString = processedValue.replace(decimalSeparator, ".")
                            var decimalValue = BigDecimal(parseableString)
                            
                            // MaxValue kontrolü
                            if (maxValue != null && decimalValue > maxValue) {
                                decimalValue = maxValue
                                textValue = decimalValue.toPlainString().replace(".", decimalSeparator)
                            }
                            
                            onValueChange(decimalValue)
                        } catch (e: NumberFormatException) {
                            // Ignored (geçersiz format, textValue güncellenmez)
                        }
                    }
                },
                placeholder = { Text(placeholder, textAlign = textAlign, modifier = Modifier.fillMaxWidth()) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester)
                    .onFocusChanged { state ->
                        isFocused = state.isFocused
                        // Focus kaybedildiğinde formatlama vs yapılabilir
                    },
                textStyle = androidx.compose.ui.text.TextStyle(textAlign = textAlign, color = textColor),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            if (unitsText != null) {
                Text(
                    text = unitsText,
                    color = unitsTextColor,
                    modifier = Modifier.clickable {
                        focusRequester.requestFocus()
                    }
                )
            }
        }
        
        // Toolbar Simülasyonu (Focus durumunda TextField altında gösterilir, iOS'taki gibi klavye üstünde asılı durması için özel insets yönetimi gerekir)
        if (isFocused) {
            Surface(
                color = TrioColor.systemGray5,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        textValue = ""
                        onValueChange(BigDecimal.ZERO)
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Clear")
                    }

                    if (showArrows) {
                        IconButton(onClick = { onPrevious?.invoke() }) {
                            Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Previous")
                        }
                        IconButton(onClick = { onNext?.invoke() }) {
                            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Next")
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    if (isDismissible) {
                        TextButton(onClick = { focusManager.clearFocus() }) {
                            Text("Done")
                        }
                    }
                }
            }
        }
    }
}
