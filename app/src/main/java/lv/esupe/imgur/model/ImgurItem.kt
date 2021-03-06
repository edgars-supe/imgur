package lv.esupe.imgur.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class ImgurItem(
    val isImage: Boolean = false,
    val isAlbum: Boolean = false
) : Parcelable {
    abstract val id: String
    abstract val title: String?
    abstract val description: String?
    abstract val thumbnail: String

    /**
     * Represents an image on Imgur.
     */
    @Parcelize
    data class Image(
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
        val link: String = ""
    ) : ImgurItem(isImage = true) {
        override val thumbnail: String
            get() {
                val idx = link.lastIndexOf('.')
                return if (idx < 0) link
                else link.take(idx) + "s" + link.drop(idx)
            }
    }

    /**
     * Represents an album on Imgur.
     */
    @Parcelize
    data class Album(
        /**
         * ID of the album.
         */
        override val id: String,
        /**
         * Title of the album. Optional.
         */
        override val title: String? = null,
        /**
         * Description of the album. Optional.
         */
        override val description: String? = null,
        /**
         * Name of the author of the album.
         */
        val author: String = "",
        /**
         * Number of upvotes this album has received.
         */
        val upvotes: Int = 0,
        /**
         * Number of downvotes this album has received.
         */
        val downvotes: Int = 0,
        /**
         * Number of times this album has been favorited.
         */
        val favorites: Int = 0,
        /**
         * Number of times this album has been viewed.
         */
        val views: Int = 0,
        /**
         * ID of the cover image for an album.
         */
        val cover: String? = null,
        /**
         * List of images contained in an album.
         */
        val images: List<Image> = emptyList()
    ) : ImgurItem(isAlbum = true) {
        override val thumbnail: String
            get() = images.first { it.id == cover }.thumbnail

        val coverLink: String
            get() = images.first().link
    }
}
