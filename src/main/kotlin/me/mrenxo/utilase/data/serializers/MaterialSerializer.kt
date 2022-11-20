package me.mrenxo.utilase.data.serializers

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.Material

object MaterialSerializer : KSerializer<Material> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("org.bukkit.Material", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Material {
        val materialName = decoder.decodeString()

        return Material.values().find { it.name.equals(materialName, true) }
            ?: throw NullPointerException("Material not found $materialName")
    }

    override fun serialize(encoder: Encoder, value: Material) {
        encoder.encodeString(value.name.toLowerCase())
    }

}