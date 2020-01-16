package lv.esupe.imgur.ui.master

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import lv.esupe.imgur.data.ImgurRepo
import lv.esupe.imgur.model.ImgurItem
import lv.esupe.imgur.ui.BaseViewModel
import lv.esupe.imgur.ui.master.model.ImgurListItem
import javax.inject.Inject

class MasterViewModel @Inject constructor(
    private val imgurRepo: ImgurRepo
) : BaseViewModel() {
    val state: Observable<MasterState>
        get() = _state
    val events: Observable<MasterEvent>
        get() = _events
    private val _state: BehaviorSubject<MasterState> =
        BehaviorSubject.createDefault(MasterState.Loading())
    private val _events: PublishSubject<MasterEvent> = PublishSubject.create()
    private val items: MutableList<ImgurItem> = mutableListOf()

    init {
        imgurRepo.getGallery("hot")
            .subscribe(
                { data ->
                    items.clear()
                    items.addAll(data.data)
                    onImagesLoaded()
                },
                { t -> _state.onNext(MasterState.Error(t.message ?: "err")) }
            )
            .bindToViewModel()
    }

    fun onItemClicked(position: Int) {
        val item = items[position]
        val event =
            if (item.isImage) MasterEvent.ShowImage(item as ImgurItem.Image)
            else MasterEvent.ShowAlbum(item as ImgurItem.Album)
        _events.onNext(event)
    }

    private fun onImagesLoaded() {
        val items = items.map { it.toListItem() }
        val state = MasterState.Content(items)
        _state.onNext(state)
    }

    private fun ImgurItem.toListItem(): ImgurListItem {
        fun ImgurItem.getTitle(): String = when {
            title != null -> title.orEmpty()
            isAlbum -> (this as ImgurItem.Album).images.firstOrNull()?.getTitle() ?: id
            else -> id
        }

        return ImgurListItem(
            id = id,
            title = getTitle(),
            link = thumbnail
        )
    }
}
