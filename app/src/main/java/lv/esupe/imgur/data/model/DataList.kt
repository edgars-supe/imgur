package lv.esupe.imgur.data.model

import kotlinx.serialization.Serializable

@Serializable
data class DataList<T>(
    val data: List<T>,
    val success: Boolean,
    val status: Int
)
