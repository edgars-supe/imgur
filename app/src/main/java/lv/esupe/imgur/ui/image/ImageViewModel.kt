package lv.esupe.imgur.ui.image

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import lv.esupe.imgur.R
import lv.esupe.imgur.model.ImgurItem
import lv.esupe.imgur.ui.BaseViewModel
import lv.esupe.imgur.utils.StringProvider
import javax.inject.Inject

class ImageViewModel @Inject constructor(
    private val stringProvider: StringProvider
) : BaseViewModel() {
    val state: Observable<ImageState>
        get() = _state
    private val _state: BehaviorSubject<ImageState> = BehaviorSubject.create()
    private var isInitialized: Boolean = false

    fun init(image: ImgurItem.Image) {
        if (isInitialized) return
        else {
            onImageSet(image)
            isInitialized = true
        }
    }

    private fun onImageSet(image: ImgurItem.Image) {
        val state = ImageState(
            title = image.title ?: stringProvider.getString(R.string.untitled),
            description = image.description,
            link = image.link,
            width = image.width,
            height = image.height
        )
        _state.onNext(state)
    }
}
