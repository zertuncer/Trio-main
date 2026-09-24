package app.trio.ui.home.viewmodel

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import kotlin.math.max
import kotlin.math.min

/**
 * ChartDataMapper
 * 
 * Equivalent to Group F (Chart Setup & Data Transformers) in iOS.
 * Handles transforming raw database entities into UI-ready chart models.
 */
class ChartDataMapper {

    data class TargetProfile(
        val value: Double,
        val startTime: Long, // Epoch millis
        val endTime: Long    // Epoch millis
    )

    data class BGTargetRaw(
        val offset: Int, // minutes from start of day
        val low: Double,
        val high: Double
    )

    /**
     * processFetchedTargets (Equivalent to GlucoseTargetSetup.swift)
     * 
     * Calculates the target profile ranges spanning from startMarker to endMarker.
     * Uses ZonedDateTime based on startMarker (NOT now()) to ensure historical accuracy 
     * across midnight boundaries and DST transitions.
     */
    fun processFetchedTargets(
        rawTargets: List<BGTargetRaw>,
        startMarker: Long,
        endMarker: Long,
        isMmolL: Boolean
    ): List<TargetProfile> {
        val targetProfiles = mutableListOf<TargetProfile>()
        
        if (rawTargets.isEmpty()) return targetProfiles

        val zoneId = ZoneId.systemDefault()
        
        // BASE DATE: Start of the day for the startMarker (NOT now())
        val baseDateZoned = Instant.ofEpochMilli(startMarker)
            .atZone(zoneId)
            .truncatedTo(ChronoUnit.DAYS)

        // Calculate how many days we span from startMarker to endMarker (minimum 3)
        val endMarkerZoned = Instant.ofEpochMilli(endMarker).atZone(zoneId)
        val daysBetween = ChronoUnit.DAYS.between(baseDateZoned, endMarkerZoned)
        val daySpan = max(3.0, (daysBetween + 1).toDouble()).toInt()

        val totalIterations = rawTargets.size * daySpan

        for (index in 0 until totalIterations) {
            val dayOffset = index / rawTargets.size
            val targetIndex = index % rawTargets.size
            val target = rawTargets[targetIndex]

            // Start time calculation using ZonedDateTime to handle DST safely
            val startTimeZoned = baseDateZoned
                .plusDays(dayOffset.toLong())
                .plusMinutes(target.offset.toLong())

            val endTimeZoned: ZonedDateTime = if (targetIndex + 1 < rawTargets.size) {
                baseDateZoned
                    .plusDays(dayOffset.toLong())
                    .plusMinutes(rawTargets[targetIndex + 1].offset.toLong())
            } else {
                baseDateZoned.plusDays(dayOffset.toLong() + 1) // Midnight of next day
            }

            val targetValue = if (isMmolL) (target.low / 18.0182) else target.low

            targetProfiles.add(
                TargetProfile(
                    value = targetValue,
                    startTime = startTimeZoned.toInstant().toEpochMilli(),
                    endTime = endTimeZoned.toInstant().toEpochMilli()
                )
            )
        }

        return targetProfiles
    }

    /**
     * GlucoseDailyDistributionStats (Equivalent to GlucoseSetup.swift -> todayGlucoseDistribution)
     * 
     * The missing 58 lines in GlucoseSetup are largely dedicated to calculating the Time-In-Range (TIR)
     * stats banner (how much % is in tight range vs high/low) for TODAY.
     * There is NO downsampling for the chart; the chart takes all points.
     */
    data class GlucoseDailyDistributionStats(
        val inSmallRangePct: Double, // The actual Tight Range (< topThreshold)
        val inRangePct: Double,      // The standard Clinical Range (< 180)
        val highPct: Double,
        val veryHighPct: Double,
        val lowPct: Double,
        val veryLowPct: Double
    )

    fun computeTodayGlucoseDistribution(
        glucoseReadings: List<Double>, 
        bottomThreshold: Double = 70.0, // from user settings (timeInRangeType.bottomThreshold)
        topThreshold: Double = 140.0    // from user settings (timeInRangeType.topThreshold)
    ): GlucoseDailyDistributionStats {
        if (glucoseReadings.isEmpty()) return GlucoseDailyDistributionStats(0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
        
        // No time-weighting, pure point count (same as iOS)
        val veryHigh = glucoseReadings.count { it > 250.0 }
        val high = glucoseReadings.count { it > 180.0 && it <= 250.0 }
        
        // Standard Clinical TIR (Always capped at 180)
        val inRange = glucoseReadings.count { it >= bottomThreshold && it <= 180.0 }
        
        // Tight Range (User's strict target, e.g., 70-140)
        val inSmallRange = glucoseReadings.count { it >= bottomThreshold && it <= topThreshold }
        
        val low = glucoseReadings.count { it < bottomThreshold && it >= 54.0 }
        val veryLow = glucoseReadings.count { it < 54.0 }
        
        val total = glucoseReadings.size.toDouble()
        
        return GlucoseDailyDistributionStats(
            inSmallRangePct = (inSmallRange / total) * 100,
            inRangePct = (inRange / total) * 100,
            highPct = (high / total) * 100,
            veryHighPct = (veryHigh / total) * 100,
            lowPct = (low / total) * 100,
            veryLowPct = (veryLow / total) * 100
        )
    }

    /**
     * updateGlucoseChartYAxis (Equivalent to ChartAxisSetup.swift)
     */
    fun calculateYAxisBounds(
        glucoseValues: List<Double>,
        forecastValues: List<Double>,
        lowGlucoseLimit: Double
    ): Pair<Double, Double> { // Pair(minY, maxY)
        val minGlucose = glucoseValues.minOrNull()
        val maxGlucose = glucoseValues.maxOrNull()

        if (minGlucose == null || maxGlucose == null) {
            return Pair(39.0, 200.0)
        }

        val minForecast = forecastValues.minOrNull()
        val maxForecast = forecastValues.maxOrNull()

        val adjustedMaxForecast = min(maxForecast ?: (maxGlucose + 50.0), maxGlucose + 50.0)
        val minOverall = min(minGlucose, minForecast ?: minGlucose)
        val maxOverall = max(maxGlucose, adjustedMaxForecast)

        var maxYValue = 200.0
        if (maxOverall > 200 && maxOverall <= 225) maxYValue = 250.0
        else if (maxOverall > 225 && maxOverall <= 275) maxYValue = 300.0
        else if (maxOverall > 275 && maxOverall <= 325) maxYValue = 350.0
        else if (maxOverall > 325) maxYValue = 400.0

        val minYValue = min(minOverall, lowGlucoseLimit - 20.0)
        
        return Pair(minYValue, maxYValue)
    }
}
