package app.trio.ui.shared

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.math.BigDecimal
import app.trio.ui.theme.TrioColor

/**
 * Setting tiplerini temsil eden Enum.
 */
sealed class SettingInputType {
    data class Decimal(val key: String) : SettingInputType()
    object Boolean : SettingInputType()
    data class ConditionalDecimal(val key: String) : SettingInputType()
}

/**
 * Orijinal iOS SettingInputSection eşdeğeri (View yapısı).
 * NOT: 42 ayar için min/max/step/units verilerini sağlayan 
 * PickerSettingsProvider aktarımı yapıldığında bu dosya onlarla tam entegre edilecektir.
 */
@Composable
fun SettingInputSection(
    decimalValue: BigDecimal,
    onDecimalChange: (BigDecimal) -> Unit,
    booleanValue: Boolean,
    onBooleanChange: (Boolean) -> Unit,
    type: SettingInputType,
    label: String,
    conditionalLabel: String? = null,
    miniHint: String,
    onHintClick: () -> Unit, // VerboseHint gösterimi tetiklenir
    headerText: String? = null,
    footerText: String? = null,
    isToggleDisabled: Boolean = false,
    miniHintColor: Color = TrioColor.secondarySystemBackground, // .secondary
    modifier: Modifier = Modifier
) {
    var displayPicker by remember { mutableStateOf(false) }
    var displayConditionalPicker by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        if (headerText != null) {
            Text(
                text = headerText.uppercase(),
                style = MaterialTheme.typography.labelMedium,
                color = TrioColor.tertiaryLabel,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        Surface(
            color = TrioColor.chart,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Main Content
                when (type) {
                    is SettingInputType.Decimal -> {
                        PickerRow(
                            label = label,
                            value = decimalValue,
                            displayPicker = displayPicker,
                            onTogglePicker = { displayPicker = !displayPicker }
                        )
                        if (displayPicker) {
                            // TODO: WheelPicker (Android'de NumberPicker eşdeğeri) eklenecektir.
                            Text("Picker Placeholder for ${type.key}")
                        }
                    }
                    is SettingInputType.Boolean -> {
                        ToggleRow(
                            label = label,
                            isOn = booleanValue,
                            onToggle = onBooleanChange,
                            isDisabled = isToggleDisabled
                        )
                    }
                    is SettingInputType.ConditionalDecimal -> {
                        ToggleRow(
                            label = label,
                            isOn = booleanValue,
                            onToggle = onBooleanChange,
                            isDisabled = isToggleDisabled
                        )
                        if (booleanValue) {
                            PickerRow(
                                label = conditionalLabel ?: label,
                                value = decimalValue,
                                displayPicker = displayConditionalPicker,
                                onTogglePicker = { displayConditionalPicker = !displayConditionalPicker }
                            )
                            if (displayConditionalPicker) {
                                // TODO: WheelPicker
                                Text("Picker Placeholder for ${type.key}")
                            }
                        }
                    }
                }

                // Hint Section
                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = miniHint,
                        style = MaterialTheme.typography.bodySmall,
                        color = miniHintColor,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = onHintClick) {
                        // questionmark.circle
                        Icon(Icons.Default.Info, contentDescription = "Info")
                    }
                }
            }
        }

        if (footerText != null) {
            Text(
                text = footerText,
                style = MaterialTheme.typography.bodySmall,
                color = TrioColor.tertiaryLabel,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}

@Composable
private fun PickerRow(
    label: String,
    value: BigDecimal,
    displayPicker: Boolean,
    onTogglePicker: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onTogglePicker() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, modifier = Modifier.weight(1f))
        Text(
            text = value.toPlainString(), // TODO: Formatlanacak
            color = if (!displayPicker) MaterialTheme.colorScheme.onSurface else TrioColor.systemBlue
        )
    }
}

@Composable
private fun ToggleRow(
    label: String,
    isOn: Boolean,
    onToggle: (Boolean) -> Unit,
    isDisabled: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, modifier = Modifier.weight(1f))
        Switch(
            checked = isOn,
            onCheckedChange = onToggle,
            enabled = !isDisabled
        )
    }
}
