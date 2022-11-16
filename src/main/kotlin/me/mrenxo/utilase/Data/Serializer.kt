package me.mrenxo.utilase.Data

data class Serializer<T>(public val serialize: (data: T) -> String, public val deserialize: (data: String) -> T)
