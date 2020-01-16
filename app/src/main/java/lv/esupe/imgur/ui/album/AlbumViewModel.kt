package lv.esupe.imgur.ui.album

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
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
    private val _state: BehaviorSubject<AlbumState> = BehaviorSubject.create()
    private var isInitialized: Boolean = false

    fun init(album: ImgurItem.Album) {
        if (isInitialized) return
        else {
            onAlbumSet(album)
            isInitialized = true
        }
    }

    private fun onAlbumSet(album: ImgurItem.Album) {
        val state = AlbumState(
            title = album.title ?: stringProvider.getString(R.string.album_untitled),
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
