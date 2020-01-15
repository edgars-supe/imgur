package lv.esupe.imgur.master

import lv.esupe.imgur.master.model.ImageItem

sealed class MasterState {
    class Loading : MasterState()
    class Content(val images: List<ImageItem>) : MasterState()
    class Error(val message: String) : MasterState()
}
