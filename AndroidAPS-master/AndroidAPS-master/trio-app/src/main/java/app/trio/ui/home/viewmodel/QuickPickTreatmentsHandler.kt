package app.trio.ui.home.viewmodel

import android.util.Log
import java.util.Calendar
import kotlin.math.abs
import kotlin.math.exp
import kotlin.math.min
import kotlin.math.pow

data class QuickPickSample(
    val amount: Float,
    val timestamp: Long
)

data class QuickPickTreatmentOutcome(
    val carbsResult: Boolean? = null,
    val bolusResult: Boolean? = null,
    val bolusFailureMessage: String? = null
)

interface IUnlockManager {
    suspend fun unlock(): Boolean
}

interface IApsManager {
    suspend fun enactBolus(amount: Double, isSMB: Boolean): Pair<Boolean, String?>
}

interface ISettingsProvider {
    fun getMaxBolusUnits(): Double
    fun getMaxCarbs(): Double
}

interface ICarbsStorage {
    suspend fun storeCarbs(carbs: Double): Boolean
}

interface IRoomAuditLogger {
    suspend fun logQuickPickTreatment(
        requestedBolus: Float?,
        cappedBolus: Double?,
        maxBolusLimit: Double,
        requestedCarbs: Float?,
        cappedCarbs: Double?,
        maxCarbLimit: Double,
        success: Boolean,
        message: String?
    )
}

class QuickPickTreatmentsHandler(
    private val unlockManager: IUnlockManager,
    private val apsManager: IApsManager,
    private val settingsProvider: ISettingsProvider,
    private val carbsStorage: ICarbsStorage,
    private val auditLogger: IRoomAuditLogger
) {

    fun topQuickPickSuggestions(
        samples: List<QuickPickSample>,
        now: Long = System.currentTimeMillis(),
        limit: Int = 5
    ): List<Float> {
        val nowCal = Calendar.getInstance().apply { timeInMillis = now }
        val nowMinute = nowCal.get(Calendar.HOUR_OF_DAY) * 60 + nowCal.get(Calendar.MINUTE)
        val nowDOW = nowCal.get(Calendar.DAY_OF_WEEK)
        
        val sigma = 60.0
        val halfLife = 10.0

        val groups = mutableMapOf<Float, Double>()

        samples.forEach { sample ->
            val roundedKey = sample.amount 
            val entryCal = Calendar.getInstance().apply { timeInMillis = sample.timestamp }
            val entryMinute = entryCal.get(Calendar.HOUR_OF_DAY) * 60 + entryCal.get(Calendar.MINUTE)
            val entryDOW = entryCal.get(Calendar.DAY_OF_WEEK)

            val diff = abs(entryMinute - nowMinute).toDouble()
            val circularDiff = min(diff, 1440.0 - diff)
            val t = exp(-(circularDiff * circularDiff) / (2.0 * sigma * sigma))

            val d = if (entryDOW == nowDOW) {
                1.0
            } else {
                val nowWeekend = (nowDOW == Calendar.SUNDAY || nowDOW == Calendar.SATURDAY)
                val entryWeekend = (entryDOW == Calendar.SUNDAY || entryDOW == Calendar.SATURDAY)
                if (nowWeekend == entryWeekend) 0.7 else 0.15
            }

            val daysAgo = (now - sample.timestamp) / 86400000.0
            val r = 0.5.pow(daysAgo / halfLife)

            val currentScore = groups[roundedKey] ?: 0.0
            groups[roundedKey] = currentScore + (t * d * r)
        }

        return groups
            .filter { it.key > 0f && it.value >= 0.1 }
            .entries
            .sortedByDescending { it.value }
            .take(limit)
            .map { it.key }
    }

    suspend fun enactQuickPickTreatment(
        bolusAmount: Float?,
        carbAmount: Float?
    ): QuickPickTreatmentOutcome {
        var carbsResult: Boolean? = null
        var cappedCarbsDelivery: Double? = null
        var maxCarbsLim = 0.0

        if (carbAmount != null && carbAmount > 0) {
            maxCarbsLim = settingsProvider.getMaxCarbs()
            val deliveryCarbs = min(carbAmount.toDouble(), maxCarbsLim)
            cappedCarbsDelivery = deliveryCarbs
            carbsResult = carbsStorage.storeCarbs(deliveryCarbs)
        }

        var bolusResult: Boolean? = null
        var bolusFailureMessage: String? = null
        var cappedBolusDelivery: Double? = null
        var maxBolusLim = 0.0

        if (bolusAmount != null && bolusAmount > 0) {
            maxBolusLim = settingsProvider.getMaxBolusUnits()
            val deliveryBolus = min(bolusAmount.toDouble(), maxBolusLim)
            cappedBolusDelivery = deliveryBolus
            
            // Fallback to DEVICE_CREDENTIAL (PIN/Password) is allowed if Biometrics are missing
            val isAuthenticated = unlockManager.unlock()
            
            if (!isAuthenticated) {
                bolusResult = false
                bolusFailureMessage = "Authentication Failed or Cancelled"
            } else {
                val result = apsManager.enactBolus(deliveryBolus, false)
                bolusResult = result.first
                bolusFailureMessage = result.second
            }
        }

        val success = (bolusResult != false) && (carbsResult != false)
        
        // Permanent Audit Log to Room Database
        auditLogger.logQuickPickTreatment(
            requestedBolus = bolusAmount,
            cappedBolus = cappedBolusDelivery,
            maxBolusLimit = maxBolusLim,
            requestedCarbs = carbAmount,
            cappedCarbs = cappedCarbsDelivery,
            maxCarbLimit = maxCarbsLim,
            success = success,
            message = bolusFailureMessage
        )

        return QuickPickTreatmentOutcome(
            carbsResult = carbsResult,
            bolusResult = bolusResult,
            bolusFailureMessage = bolusFailureMessage
        )
    }
}
