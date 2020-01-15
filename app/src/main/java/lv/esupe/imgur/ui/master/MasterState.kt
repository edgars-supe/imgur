package lv.esupe.imgur.ui.master

import lv.esupe.imgur.ui.master.model.ImageItem

sealed class MasterState {
    class Loading : MasterState()
    class Content(val images: List<ImageItem>) : MasterState()
    class Error(val message: String) : MasterState()
}

sealed class MasterEvent {
    class ShowImage(val id: String) : MasterEvent()
}
