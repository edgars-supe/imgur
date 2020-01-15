package lv.esupe.imgur.ui.master

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import lv.esupe.imgur.data.ImgurRepo
import lv.esupe.imgur.model.Image
import lv.esupe.imgur.ui.BaseViewModel
import lv.esupe.imgur.ui.master.model.ImageItem
import javax.inject.Inject

class MasterViewModel @Inject constructor(
    private val imgurRepo: ImgurRepo
) : BaseViewModel() {
    val state: Observable<MasterState>
        get() = _state
    private val _state: BehaviorSubject<MasterState> =
        BehaviorSubject.createDefault(MasterState.Loading())
    private val images: MutableList<Image> = mutableListOf()

    init {
        imgurRepo.getGallery("hot")
            .subscribe(
                { data ->
                    images.clear()
                    images.addAll(data.data)
                    onImagesLoaded()
                },
                { t -> _state.onNext(MasterState.Error(t.message ?: "err")) }
            )
            .bindToViewModel()
    }

    fun onImageClicked(position: Int) {
        // TODO
    }

    private fun onImagesLoaded() {
        val items = images.map { it.toImageItem() }
        val state = MasterState.Content(items)
        _state.onNext(state)
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
