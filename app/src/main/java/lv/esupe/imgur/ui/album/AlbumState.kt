package lv.esupe.imgur.ui.album

import lv.esupe.imgur.ui.album.model.AlbumImage

data class AlbumState(
    val title: String,
    val author: String,
    val upvotes: Int,
    val downvotes: Int,
    val views: Int,
    val favorites: Int,
    val coverLink: String,
    val images: List<AlbumImage>
)