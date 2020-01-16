package lv.esupe.imgur.ui.album

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import lv.esupe.imgur.R
import lv.esupe.imgur.model.ImgurItem
import lv.esupe.imgur.ui.BaseViewModel
import lv.esupe.imgur.ui.album.model.AlbumImage
import lv.esupe.imgur.utils.StringProvider
import javax.inject.Inject

class AlbumViewModel @Inject constructor(
    private val stringProvider: StringProvider
) : BaseViewModel() {

    val state: Observable<AlbumState>
        get() = _state
    val events: Observable<AlbumEvent>
        get() = _events
    private val _state: BehaviorSubject<AlbumState> = BehaviorSubject.create()
    private val _events: PublishSubject<AlbumEvent> = PublishSubject.create()
    private var isInitialized: Boolean = false
    private lateinit var album: ImgurItem.Album

    fun init(album: ImgurItem.Album) {
        if (isInitialized) return
        else {
            onAlbumSet(album)
            isInitialized = true
        }
    }

    fun onItemClicked(position: Int) {
        val image = album.images[position]
        _events.onNext(AlbumEvent.ShowImage(image))
    }

    private fun onAlbumSet(album: ImgurItem.Album) {
        this.album = album
        val state = AlbumState(
            title = album.title ?: stringProvider.getString(R.string.untitled),
            description = album.description,
            author = album.author,
            upvotes = album.upvotes,
            downvotes = album.downvotes,
            views = album.views,
            favorites = album.favorites,
            coverLink = album.coverLink,
            images = album.images.map { AlbumImage(it.link) }
        )
        _state.onNext(state)
    }
}
