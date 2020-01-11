package lv.esupe.imgur.model

import kotlinx.serialization.Serializable

@Serializable
data class Data<T>(
    val data: T,
    val success: Boolean,
    val status: Int
)
