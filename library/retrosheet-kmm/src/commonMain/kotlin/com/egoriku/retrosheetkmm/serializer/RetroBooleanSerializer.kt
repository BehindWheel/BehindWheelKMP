package com.egoriku.retrosheetkmm.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object RetroBooleanSerializer : KSerializer<Boolean> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(
            serialName = "com.egoriku.retrosheetkmm.serializer.RetroBooleanSerializer",
            kind = PrimitiveKind.BOOLEAN
        )

    override fun serialize(encoder: Encoder, value: Boolean) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): Boolean {
        return decoder.decodeString().toBoolean()
    }
}