package lv.esupe.imgur

import lv.esupe.imgur.model.Image

interface Navigator {
    fun showImage(image: Image)
    fun showAlbum(image: Image)
    fun returnToMaster()
}
