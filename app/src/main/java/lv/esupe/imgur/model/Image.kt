package lv.esupe.imgur.model

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val id: String,
    val link: String
)
