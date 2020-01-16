package lv.esupe.imgur.ui.master.model

data class ImgurListItem(
    val id: String,
    val title: String,
    val link: String,
    val isAlbum: Boolean,
    val albumSize: Int
)
