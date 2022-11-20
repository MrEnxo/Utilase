package me.mrenxo.utilase.data

data class Serializer<T>(public val serialize: (data: T) -> String, public val deserialize: (data: String) -> T)
