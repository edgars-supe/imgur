package lv.esupe.imgur.ui.album

import lv.esupe.imgur.model.ImgurItem
import lv.esupe.imgur.ui.album.model.AlbumImage

data class AlbumState(
    val title: String,
    val description: String?,
    val author: String,
    val upvotes: Int,
    val downvotes: Int,
    val views: Int,
    val favorites: Int,
    val coverLink: String,
    val images: List<AlbumImage>
)

sealed class AlbumEvent {
    class ShowImage(val image: ImgurItem.Image) : AlbumEvent()
}
