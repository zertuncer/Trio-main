package app.trio.kits.g7.messages

import java.nio.ByteBuffer
import java.nio.ByteOrder

data class G7GlucoseMessage(
    val messageTimestamp: Long,
    val sequence: Int,
    val age: Int,
    val glucose: Int?,
    val predicted: Int?,
    val glucoseIsDisplayOnly: Boolean,
    val algorithmState: AlgorithmState,
    val trend: Double?,
    val rawData: ByteArray
) {
    val hasReliableGlucose: Boolean
        get() = algorithmState.hasReliableGlucose

    val glucoseTimestamp: Long
        get() = messageTimestamp - age

    companion object {
        fun parse(data: ByteArray): G7GlucoseMessage? {
            if (data.size < 19) return null
            if (data[1].toInt() != 0x00) return null

            val buffer = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN)

            // TTTTTTTT = timestamp (Bytes 2..5)
            val messageTimestamp = buffer.getInt(2).toUInt().toLong()

            // SQSQ = sequence (Bytes 6..7)
            val sequence = buffer.getShort(6).toUShort().toInt()

            // AGAG = age (Bytes 10..11)
            val age = buffer.getShort(10).toUShort().toInt()

            // BGBG = glucose (Bytes 12..13)
            val glucoseData = buffer.getShort(12).toUShort().toInt()
            val glucose = if (glucoseData != 0xFFFF) glucoseData and 0xFFF else null
            val glucoseIsDisplayOnly = if (glucoseData != 0xFFFF) (data[18].toInt() and 0x10) > 0 else false

            // PRPR = predicted (Bytes 16..17)
            val predictionData = buffer.getShort(16).toUShort().toInt()
            val predicted = if (predictionData != 0xFFFF) predictionData and 0xFFF else null

            // SS = algorithm state (Byte 14)
            val algorithmState = AlgorithmState.fromByte(data[14])

            // TR = trend (Byte 15)
            val trendByte = data[15].toInt()
            val trend = if (trendByte == 0x7F) null else trendByte.toDouble() / 10.0

            return G7GlucoseMessage(
                messageTimestamp = messageTimestamp,
                sequence = sequence,
                age = age,
                glucose = glucose,
                predicted = predicted,
                glucoseIsDisplayOnly = glucoseIsDisplayOnly,
                algorithmState = algorithmState,
                trend = trend,
                rawData = data
            )
        }
    }
}
