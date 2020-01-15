package lv.esupe.imgur.ui.master

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import lv.esupe.imgur.data.GalleryRepo
import lv.esupe.imgur.ui.master.model.ImageItem
import lv.esupe.imgur.model.Image
import javax.inject.Inject

class MasterViewModel @Inject constructor(
    private val galleryRepo: GalleryRepo
) : ViewModel() {
    val state: LiveData<MasterState>
        get() = _state
    private val _state: MutableLiveData<MasterState> = MutableLiveData(MasterState.Loading())
    private var disposable: Disposable = Disposables.disposed()
    private val images: MutableList<Image> = mutableListOf()

    init {
        disposable = galleryRepo.getGallery("hot")
            .subscribe(
                { data ->
                    images.clear()
                    images.addAll(data.data)
                    onImagesLoaded()
                },
                { t -> _state.postValue(MasterState.Error(t.message ?: "err")) }
            )

    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    fun onImageClicked(position: Int) {
        // TODO
    }

    private fun onImagesLoaded() {
        val items = images.map { it.toImageItem() }
        val state = MasterState.Content(items)
        _state.postValue(state)
    }

    private fun Image.toImageItem(): ImageItem {
        fun Image.getTitle(): String = when {
            title != null -> title
            description != null -> description
            isAlbum -> images.firstOrNull()?.getTitle() ?: id
            else -> id
        }

        fun Image.getLink(): String =
            if (isAlbum) images.firstOrNull { it.id == cover }?.link ?: link
            else link

        return ImageItem(
            id = id,
            title = getTitle(),
            width = if (isAlbum) coverWidth else width,
            height = if (isAlbum) coverHeight else height,
            link = getLink()
        )
    }
}
