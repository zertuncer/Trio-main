package app.trio.kits.libre

data class LibreGlucose(
    val unsmoothedGlucose: Double,
    val glucoseDouble: Double,
    val timestamp: Long
) {
    val glucose: Int
        get() = Math.round(glucoseDouble).toInt()

    val isStateValid: Boolean
        get() = glucose >= 1

    companion object {
        fun calculateSlopeByMinute(current: LibreGlucose, last: LibreGlucose): Double {
            if (current.timestamp == last.timestamp) return 0.0
            
            val curr = current.timestamp.toDouble() * 1000.0
            val lastTime = last.timestamp.toDouble() * 1000.0
            
            val slopeMs = (last.unsmoothedGlucose - current.unsmoothedGlucose) / (lastTime - curr)
            return slopeMs * 60000.0
        }

        fun getGlucoseTrend(current: LibreGlucose?, last: LibreGlucose?): String {
            if (current == null || last == null) return "→"

            val s = calculateSlopeByMinute(current, last)

            return when {
                s <= -3.5 -> "↓↓"
                s <= -2.0 -> "↓"
                s <= -1.0 -> "↘"
                s <= 1.0 -> "→"
                s <= 2.0 -> "↗"
                s <= 3.5 -> "↑"
                s <= 40.0 -> "→"
                else -> "→"
            }
        }
    }
}
