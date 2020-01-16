package lv.esupe.imgur.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class ImgurItem(
    val isImage: Boolean = false,
    val isAlbum: Boolean = true
) : Parcelable {
    abstract val id: String
    abstract val title: String?
    abstract val description: String?
    abstract val thumbnail: String

    /**
     * Represents an image on Imgur.
     */
    @Parcelize
    class Image(
        /**
         * ID of the image.
         */
        override val id: String,
        /**
         * Title of the image. Optional.
         */
        override val title: String? = null,
        /**
         * Description of the image. Optional.
         */
        override val description: String? = null,
        /**
         * Width of the image.
         */
        val width: Int = 0,
        /**
         * Height of the image.
         */
        val height: Int = 0,
        /**
         * Link to the image.
         */
        val link: String
    ) : ImgurItem(isImage = true) {
        override val thumbnail: String
            get() {
                val idx = link.lastIndexOf('.')
                return link.take(idx) + "s" + link.drop(idx)
            }
    }

    /**
     * Represents an album on Imgur.
     */
    @Parcelize
    class Album(
        /**
         * ID of the album.
         */
        override val id: String,
        /**
         * Title of the image album. Optional.
         */
        override val title: String? = null,
        /**
         * Description of the album. Optional.
         */
        override val description: String? = null,
        /**
         * ID of the cover image for an album.
         */
        val cover: String? = null,
        /**
         * Width of the cover image for an album.
         */
        val coverWidth: Int = 0,
        /**
         * Height of the cover image for an album.
         */
        val coverHeight: Int = 0,
        /**
         * List of images contained in an album.
         */
        val images: List<Image> = emptyList()
    ) : ImgurItem(isAlbum = true) {
        override val thumbnail: String
            get() = images.first { it.id == cover }.thumbnail
    }
}
