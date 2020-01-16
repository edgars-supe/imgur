package lv.esupe.imgur.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class ImgurItem(
    val isImage: Boolean = false,
    val isAlbum: Boolean = false
) : Parcelable {
    abstract val id: String
    abstract val title: String?
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
        val description: String? = null,
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
         * Name of the author of the album.
         */
        val author: String,
        /**
         * Number of upvotes this album has received.
         */
        val upvotes: Int,
        /**
         * Number of downvotes this album has received.
         */
        val downvotes: Int,
        /**
         * Number of times this album has been favorited.
         */
        val favorites: Int,
        /**
         * Number of times this album has been viewed.
         */
        val views: Int,
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

        val coverLink: String
            get() = images.first().link
    }
}
