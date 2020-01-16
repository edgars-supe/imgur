package lv.esupe.imgur.ui.master

import lv.esupe.imgur.model.ImgurItem
import lv.esupe.imgur.ui.master.model.ImgurListItem

sealed class MasterState {
    class Loading(val title: String) : MasterState()
    class Content(
        val title: String,
        val images: List<ImgurListItem>
    ) : MasterState()
    class Error(val message: String) : MasterState()
}

sealed class MasterEvent {
    class ShowImage(val image: ImgurItem.Image) : MasterEvent()
    class ShowAlbum(val album: ImgurItem.Album) : MasterEvent()
}
