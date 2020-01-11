package lv.esupe.imgur.master

import lv.esupe.imgur.model.Image

sealed class MasterState {
    class Loading : MasterState()
    class Content(val data: List<Image>) : MasterState()
    class Error(val message: String) : MasterState()
}
