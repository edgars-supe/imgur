package lv.esupe.imgur

import lv.esupe.imgur.model.ImgurItem

interface Navigator {
    fun showImage(image: ImgurItem.Image)
    fun showAlbum(album: ImgurItem.Album)
    fun returnToMaster()
}
