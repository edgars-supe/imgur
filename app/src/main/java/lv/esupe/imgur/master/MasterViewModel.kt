package lv.esupe.imgur.master

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import lv.esupe.imgur.data.GalleryRepo
import javax.inject.Inject

class MasterViewModel @Inject constructor(
    private val galleryRepo: GalleryRepo
) : ViewModel() {
    val state: LiveData<MasterState>
        get() = _state
    private val _state: MutableLiveData<MasterState> = MutableLiveData(MasterState.Loading())
    private var disposable: Disposable = Disposables.disposed()

    init {
        disposable = galleryRepo.getGallery("hot")
            .subscribe(
                { data ->
                    val state = MasterState.Content(data.data)
                    _state.postValue(state)
                },
                { t -> _state.postValue(MasterState.Error(t.message ?: "err")) }
            )

    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}
