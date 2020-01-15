package lv.esupe.imgur.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents an Image or Album entity on Imgur.
 */
@Serializable
data class Image(
    /**
     * ID of the image or album.
     */
    val id: String,
    /**
     * Title of the image or album. Optional.
     */
    val title: String? = null,
    /**
     * Description of the image or album. Optional.
     */
    val description: String? = null,
    /**
     * Width of the image. Not present on albums.
     */
    val width: Int = 0,
    /**
     * Height of the image. Not presents on albums.
     */
    val height: Int = 0,
    /**
     * Link to the image or album.
     */
    val link: String,
    /**
     * Denotes whether this item is an image or an album.
     */
    @SerialName("is_album") val isAlbum: Boolean = false,
    /**
     * ID of the cover image for an album. Not present on images.
     */
    val cover: String? = null,
    /**
     * Width of the cover image for an album. Not present on images.
     */
    @SerialName("cover_width") val coverWidth: Int = 0,
    /**
     * Height of the cover image for an album. Not present on images.
     */
    @SerialName("cover_height") val coverHeight: Int = 0,
    /**
     * List of images contained in an album. Not present on images.
     */
    val images: List<Image> = emptyList()
) {

    /**
     * Returns a link to a small thumbnail of the image or album cover.
     */
    val thumbnail: String
        get() =
            if (isAlbum) {
                images.first { it.id == cover }.thumbnail
            } else {
                val idx = link.lastIndexOf('.')
                link.take(idx) + "s" + link.drop(idx)
            }
}
