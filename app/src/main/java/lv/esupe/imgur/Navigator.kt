package lv.esupe.imgur

import lv.esupe.imgur.model.ImgurItem

interface Navigator {
    fun showImage(image: ImgurItem.Image)
    fun showAlbum(image: ImgurItem.Album)
    fun returnToMaster()
}
