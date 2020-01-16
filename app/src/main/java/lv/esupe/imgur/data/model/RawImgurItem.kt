package lv.esupe.imgur.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import lv.esupe.imgur.model.ImgurItem

/**
 * This class is used to parse album and image info from the Imgur API. It contains fields for both
 * types, because the structures are very similar and the main object list in a gallery can contain
 * both simultaneously.
 */
@Serializable
data class RawImgurItem(
    val id: String,
    val title: String? = null,
    val description: String? = null,
    val width: Int = 0,
    val height: Int = 0,
    val link: String,
    val ups: Int? = null,
    val downs: Int? = null,
    @SerialName("favorite_count") val favorites: Int? = null,
    val views: Int? = null,
    @SerialName("is_album") val isAlbum: Boolean = false,
    @SerialName("account_url") val accountName: String? = null,
    val cover: String? = null,
    @SerialName("cover_width") val coverWidth: Int = 0,
    @SerialName("cover_height") val coverHeight: Int = 0,
    val images: List<RawImgurItem> = emptyList()
) {
    fun toModel(): ImgurItem = if (isAlbum) toAlbum() else toImage()

    private fun toAlbum(): ImgurItem.Album {
        val modelImages = images.map { it.toModel() }.filterIsInstance<ImgurItem.Image>()
        return ImgurItem.Album(
            id = id,
            title = title,
            author = accountName.orEmpty(),
            upvotes = ups ?: 0,
            downvotes = downs ?: 0,
            favorites = favorites ?: 0,
            views = views ?: 0,
            cover = cover,
            images = modelImages
        )
    }

    private fun toImage(): ImgurItem.Image =
        ImgurItem.Image(
            id = id,
            title = title,
            description = description,
            width = width,
            height = height,
            link = link
        )
}
