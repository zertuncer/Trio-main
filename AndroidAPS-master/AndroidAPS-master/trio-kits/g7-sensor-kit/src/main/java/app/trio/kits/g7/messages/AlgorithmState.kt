package app.trio.kits.g7.messages

enum class AlgorithmState(val rawValue: Byte) {
    Stopped(1),
    Warmup(2),
    ExcessNoise(3),
    FirstOfTwoBGsNeeded(4),
    SecondOfTwoBGsNeeded(5),
    Ok(6),
    NeedsCalibration(7),
    CalibrationError1(8),
    CalibrationError2(9),
    CalibrationLinearityFitFailure(10),
    SensorFailedDueToCountsAberration(11),
    SensorFailedDueToResidualAberration(12),
    OutOfCalibrationDueToOutlier(13),
    OutlierCalibrationRequest(14),
    SessionExpired(15),
    SessionFailedDueToUnrecoverableError(16),
    SessionFailedDueToTransmitterError(17),
    TemporarySensorIssue(18),
    SensorFailedDueToProgressiveSensorDecline(19),
    SensorFailedDueToHighCountsAberration(20),
    SensorFailedDueToLowCountsAberration(21),
    SensorFailedDueToRestart(22),
    Expired(24),
    SensorFailed(25),
    SessionEnded(26),
    Unknown(-1); // For unknown raw values

    val isSensorFailed: Boolean
        get() = this == SensorFailed ||
                this == SensorFailedDueToCountsAberration ||
                this == SensorFailedDueToResidualAberration ||
                this == SessionFailedDueToTransmitterError ||
                this == SessionFailedDueToUnrecoverableError ||
                this == SensorFailedDueToProgressiveSensorDecline ||
                this == SensorFailedDueToHighCountsAberration ||
                this == SensorFailedDueToLowCountsAberration ||
                this == SensorFailedDueToRestart

    val isInWarmup: Boolean
        get() = this == Warmup

    val hasTemporaryError: Boolean
        get() = this == TemporarySensorIssue

    val hasReliableGlucose: Boolean
        get() = this == Ok || this == NeedsCalibration

    val hasError: Boolean
        get() = this == ExcessNoise || this == CalibrationError1 || this == CalibrationError2 || this == CalibrationLinearityFitFailure || isSensorFailed

    val closedLoopState: Int
        get() = when (this) {
            Ok -> 0
            NeedsCalibration -> 1
            else -> 2 // Unreliable
        }

    companion object {
        fun fromByte(rawValue: Byte): AlgorithmState {
            return entries.find { it.rawValue == rawValue } ?: Unknown
        }
    }
}
