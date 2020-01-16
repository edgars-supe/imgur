package lv.esupe.imgur.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import lv.esupe.imgur.model.ImgurItem

@Serializable
data class RawImgurItem(
    val id: String,
    val title: String? = null,
    val description: String? = null,
    val width: Int = 0,
    val height: Int = 0,
    val link: String,
    @SerialName("is_album") val isAlbum: Boolean = false,
    val cover: String? = null,
    @SerialName("cover_width") val coverWidth: Int = 0,
    @SerialName("cover_height") val coverHeight: Int = 0,
    val images: List<RawImgurItem> = emptyList()
) {
    fun toModel(): ImgurItem =
        if (isAlbum) {
            val modelImages = images.map { it.toModel() }.filterIsInstance<ImgurItem.Image>()
            ImgurItem.Album(id, title, description, cover, coverWidth, coverHeight, modelImages)
        } else {
            ImgurItem.Image(id, title, description, width, height, link)
        }
}
