package app.trio.kits.g7.messages

enum class G7Opcode(val value: Byte) {
    AuthChallengeRx(0x05.toByte()),
    SessionStopTx(0x28.toByte()),
    GlucoseTx(0x4E.toByte()),
    ExtendedVersionTx(0x52.toByte()),
    ExtendedVersionRx(0x53.toByte()),
    BackfillFinished(0x59.toByte());

    companion object {
        fun fromByte(value: Byte): G7Opcode? = entries.find { it.value == value }
    }
}
