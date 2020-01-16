package lv.esupe.imgur

interface Navigator {
    fun showImage(id: String)
    fun showAlbum(id: String)
    fun returnToMaster()
}
