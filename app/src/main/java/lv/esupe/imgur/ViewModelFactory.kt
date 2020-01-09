package lv.esupe.imgur

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import lv.esupe.imgur.details.DetailsViewModel
import lv.esupe.imgur.master.MasterViewModel

class ViewModelFactory(val navigator: Navigator) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        MasterViewModel::class.java -> MasterViewModel() as T
        DetailsViewModel::class.java -> DetailsViewModel() as T
        else -> error("Unsupported ViewModel class $modelClass")
    }
}
