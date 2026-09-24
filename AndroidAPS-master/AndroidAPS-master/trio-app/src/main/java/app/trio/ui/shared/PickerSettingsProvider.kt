package app.trio.ui.shared

import java.math.BigDecimal
import java.math.RoundingMode

enum class PickerSettingType {
    Glucose, Factor, Gram, InsulinUnit, InsulinUnitPerHour, Minute, Hour
}

data class PickerSetting(
    val value: BigDecimal,
    val step: BigDecimal,
    val min: BigDecimal,
    val max: BigDecimal,
    val type: PickerSettingType
)

data class DecimalPickerSettings(
    val carbsRequiredThreshold: PickerSetting = PickerSetting(BigDecimal("10"), BigDecimal("1"), BigDecimal("0"), BigDecimal("100"), PickerSettingType.Gram),
    val individualAdjustmentFactor: PickerSetting = PickerSetting(BigDecimal("0.5"), BigDecimal("0.05"), BigDecimal("0.1"), BigDecimal("1.2"), PickerSettingType.Factor),
    val high: PickerSetting = PickerSetting(BigDecimal("180"), BigDecimal("1"), BigDecimal("100"), BigDecimal("400"), PickerSettingType.Glucose),
    val low: PickerSetting = PickerSetting(BigDecimal("70"), BigDecimal("1"), BigDecimal("40"), BigDecimal("100"), PickerSettingType.Glucose),
    val maxCarbs: PickerSetting = PickerSetting(BigDecimal("250"), BigDecimal("5"), BigDecimal("0"), BigDecimal("300"), PickerSettingType.Gram),
    val maxFat: PickerSetting = PickerSetting(BigDecimal("250"), BigDecimal("5"), BigDecimal("0"), BigDecimal("300"), PickerSettingType.Gram),
    val maxProtein: PickerSetting = PickerSetting(BigDecimal("250"), BigDecimal("5"), BigDecimal("0"), BigDecimal("300"), PickerSettingType.Gram),
    val overrideFactor: PickerSetting = PickerSetting(BigDecimal("0.8"), BigDecimal("0.05"), BigDecimal("0.05"), BigDecimal("1.5"), PickerSettingType.Factor),
    val fattyMealFactor: PickerSetting = PickerSetting(BigDecimal("0.7"), BigDecimal("0.05"), BigDecimal("0.05"), BigDecimal("1"), PickerSettingType.Factor),
    val sweetMealFactor: PickerSetting = PickerSetting(BigDecimal("1"), BigDecimal("0.05"), BigDecimal("0.05"), BigDecimal("2"), PickerSettingType.Factor),
    val maxIOB: PickerSetting = PickerSetting(BigDecimal("0"), BigDecimal("1"), BigDecimal("0"), BigDecimal("30"), PickerSettingType.InsulinUnit),
    val maxDailySafetyMultiplier: PickerSetting = PickerSetting(BigDecimal("3"), BigDecimal("0.1"), BigDecimal("1"), BigDecimal("5"), PickerSettingType.Factor),
    val currentBasalSafetyMultiplier: PickerSetting = PickerSetting(BigDecimal("4"), BigDecimal("0.1"), BigDecimal("1"), BigDecimal("5"), PickerSettingType.Factor),
    val autosensMax: PickerSetting = PickerSetting(BigDecimal("1.2"), BigDecimal("0.05"), BigDecimal("0.5"), BigDecimal("2"), PickerSettingType.Factor),
    val autosensMin: PickerSetting = PickerSetting(BigDecimal("0.7"), BigDecimal("0.05"), BigDecimal("0.5"), BigDecimal("1"), PickerSettingType.Factor),
    val smbDeliveryRatio: PickerSetting = PickerSetting(BigDecimal("0.5"), BigDecimal("0.05"), BigDecimal("0.3"), BigDecimal("0.7"), PickerSettingType.Factor),
    val halfBasalExerciseTarget: PickerSetting = PickerSetting(BigDecimal("160"), BigDecimal("5"), BigDecimal("105"), BigDecimal("300"), PickerSettingType.Glucose),
    val maxCOB: PickerSetting = PickerSetting(BigDecimal("120"), BigDecimal("5"), BigDecimal("0"), BigDecimal("300"), PickerSettingType.Gram),
    val maxMealAbsorptionTime: PickerSetting = PickerSetting(BigDecimal("6"), BigDecimal("1"), BigDecimal("4"), BigDecimal("10"), PickerSettingType.Hour),
    val min5mCarbimpact: PickerSetting = PickerSetting(BigDecimal("8"), BigDecimal("1"), BigDecimal("1"), BigDecimal("20"), PickerSettingType.Glucose),
    val remainingCarbsFraction: PickerSetting = PickerSetting(BigDecimal("1.0"), BigDecimal("0.05"), BigDecimal("0.5"), BigDecimal("1"), PickerSettingType.Factor),
    val remainingCarbsCap: PickerSetting = PickerSetting(BigDecimal("90"), BigDecimal("5"), BigDecimal("0"), BigDecimal("200"), PickerSettingType.Gram),
    val maxSMBBasalMinutes: PickerSetting = PickerSetting(BigDecimal("30"), BigDecimal("5"), BigDecimal("15"), BigDecimal("180"), PickerSettingType.Minute),
    val maxUAMSMBBasalMinutes: PickerSetting = PickerSetting(BigDecimal("30"), BigDecimal("5"), BigDecimal("15"), BigDecimal("180"), PickerSettingType.Minute),
    val smbInterval: PickerSetting = PickerSetting(BigDecimal("3"), BigDecimal("1"), BigDecimal("1"), BigDecimal("10"), PickerSettingType.Minute),
    val bolusIncrement: PickerSetting = PickerSetting(BigDecimal("0.1"), BigDecimal("0.05"), BigDecimal("0.05"), BigDecimal("1"), PickerSettingType.InsulinUnit),
    val insulinPeakTime: PickerSetting = PickerSetting(BigDecimal("75"), BigDecimal("1"), BigDecimal("35"), BigDecimal("120"), PickerSettingType.Minute),
    val carbsReqThreshold: PickerSetting = PickerSetting(BigDecimal("1.0"), BigDecimal("0.1"), BigDecimal("0"), BigDecimal("10"), PickerSettingType.Gram),
    val noisyCGMTargetMultiplier: PickerSetting = PickerSetting(BigDecimal("1.3"), BigDecimal("0.05"), BigDecimal("1"), BigDecimal("2"), PickerSettingType.Factor),
    val maxDeltaBGthreshold: PickerSetting = PickerSetting(BigDecimal("0.2"), BigDecimal("0.05"), BigDecimal("0.1"), BigDecimal("0.4"), PickerSettingType.Factor),
    val adjustmentFactor: PickerSetting = PickerSetting(BigDecimal("0.8"), BigDecimal("0.05"), BigDecimal("0.3"), BigDecimal("3.0"), PickerSettingType.Factor),
    val adjustmentFactorSigmoid: PickerSetting = PickerSetting(BigDecimal("0.5"), BigDecimal("0.05"), BigDecimal("0.1"), BigDecimal("2"), PickerSettingType.Factor),
    val weightPercentage: PickerSetting = PickerSetting(BigDecimal("0.35"), BigDecimal("0.05"), BigDecimal("0.05"), BigDecimal("1"), PickerSettingType.Factor),
    val enableSMB_high_bg_target: PickerSetting = PickerSetting(BigDecimal("110"), BigDecimal("1"), BigDecimal("70"), BigDecimal("200"), PickerSettingType.Glucose),
    val threshold_setting: PickerSetting = PickerSetting(BigDecimal("60"), BigDecimal("1"), BigDecimal("60"), BigDecimal("120"), PickerSettingType.Glucose),
    val updateInterval: PickerSetting = PickerSetting(BigDecimal("20"), BigDecimal("5"), BigDecimal("1"), BigDecimal("60"), PickerSettingType.Minute),
    val delay: PickerSetting = PickerSetting(BigDecimal("60"), BigDecimal("5"), BigDecimal("15"), BigDecimal("120"), PickerSettingType.Minute),
    val minuteInterval: PickerSetting = PickerSetting(BigDecimal("30"), BigDecimal("5"), BigDecimal("30"), BigDecimal("60"), PickerSettingType.Minute),
    val hours: PickerSetting = PickerSetting(BigDecimal("6"), BigDecimal("0.5"), BigDecimal("2"), BigDecimal("24"), PickerSettingType.Hour),
    val dia: PickerSetting = PickerSetting(BigDecimal("10"), BigDecimal("0.5"), BigDecimal("5"), BigDecimal("10"), PickerSettingType.Hour),
    val maxBolus: PickerSetting = PickerSetting(BigDecimal("10"), BigDecimal("0.5"), BigDecimal("0.5"), BigDecimal("30"), PickerSettingType.InsulinUnit),
    val maxBasal: PickerSetting = PickerSetting(BigDecimal("10"), BigDecimal("0.5"), BigDecimal("0.5"), BigDecimal("30"), PickerSettingType.InsulinUnitPerHour)
)

object PickerSettingsProvider {
    val settings = DecimalPickerSettings()

    fun generatePickerValues(setting: PickerSetting, isMmolL: Boolean = false): List<BigDecimal> {
        val values = mutableListOf<BigDecimal>()
        var currentValue = setting.min

        while (currentValue <= setting.max) {
            values.add(currentValue)
            currentValue += setting.step
        }

        // Glucose values are stored as mg/dl values.
        // Filter out duplicate values when rounded to 1 decimal place.
        if (isMmolL && setting.type == PickerSettingType.Glucose) {
            val uniqueRoundedValues = mutableSetOf<String>()
            return values.filter { value ->
                // iOS asMmolL logic: value * 0.0555
                val mmolValue = value.multiply(BigDecimal("0.0555"))
                val roundedValue = mmolValue.setScale(1, RoundingMode.HALF_UP).toPlainString()
                uniqueRoundedValues.add(roundedValue)
            }
        }

        return values
    }
}
