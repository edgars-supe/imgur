package lv.esupe.imgur.ui.master

import android.util.Log
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import kotlinx.io.IOException
import lv.esupe.imgur.R
import lv.esupe.imgur.data.ImgurRepo
import lv.esupe.imgur.model.ImgurItem
import lv.esupe.imgur.model.Section
import lv.esupe.imgur.ui.BaseViewModel
import lv.esupe.imgur.ui.master.model.ImgurListItem
import lv.esupe.imgur.utils.StringProvider
import javax.inject.Inject

class MasterViewModel @Inject constructor(
    private val imgurRepo: ImgurRepo,
    private val stringProvider: StringProvider
) : BaseViewModel() {
    val state: Observable<MasterState>
        get() = _state
    val events: Observable<MasterEvent>
        get() = _events

    private val _state: BehaviorSubject<MasterState> = BehaviorSubject.create()
    private val _events: PublishSubject<MasterEvent> = PublishSubject.create()

    private var section: Section = Section.Hot
    private val items: MutableList<ImgurItem> = mutableListOf()
    private val title: String
        get() {
            val sectionTitle = stringProvider.getString(section.resId)
            return stringProvider.getString(R.string.master_title, sectionTitle)
        }

    init {
        _state.onNext(MasterState.Loading(title))
        loadGallery()
    }

    fun onItemClicked(position: Int) {
        val item = items[position]
        val event =
            if (item.isImage) MasterEvent.ShowImage(item as ImgurItem.Image)
            else MasterEvent.ShowAlbum(item as ImgurItem.Album)
        _events.onNext(event)
    }

    fun onTryAgainClicked() {
        loadGallery()
    }

    private fun loadGallery() {
        imgurRepo.getGallery(section)
            .subscribe(
                { data ->
                    items.clear()
                    items.addAll(data.data)
                    onImagesLoaded()
                },
                { t ->
                    onError(t)
                }
            )
            .bindToViewModel()
    }

    private fun onError(t: Throwable) {
        Log.e("MasterViewModel", t.message, t)
        val message =
            if (t is IOException) stringProvider.getString(R.string.network_error)
            else stringProvider.getString(R.string.unknown_error)
        _state.onNext(MasterState.Error(message))
    }

    private fun onImagesLoaded() {
        val items = items.map { it.toListItem() }
        val state = MasterState.Content(title, items)
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
            link = thumbnail,
            isAlbum = isAlbum,
            albumSize = (this as? ImgurItem.Album)?.images?.size ?: 0
        )
    }
}
