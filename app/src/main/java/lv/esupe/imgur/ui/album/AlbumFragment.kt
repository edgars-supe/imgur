package lv.esupe.imgur.ui.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import kotlinx.android.synthetic.main.fragment_album.*
import lv.esupe.imgur.R
import lv.esupe.imgur.model.ImgurItem
import lv.esupe.imgur.utils.component
import lv.esupe.imgur.utils.toolbarController
import lv.esupe.imgur.utils.viewModel

class AlbumFragment : Fragment() {

    companion object {
        private const val EXTRA_ALBUM = "ALBUM"

        fun newInstance(album: ImgurItem.Album) = AlbumFragment().apply {
            arguments = bundleOf(EXTRA_ALBUM to album)
        }
    }

    private val viewModel by viewModel { component().albumViewModel }
    private var stateDisposable: Disposable = Disposables.disposed()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbarController.enableBackButton()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_album, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val album = arguments?.getParcelable<ImgurItem.Album>(EXTRA_ALBUM)
            ?: error("Couldn't read album")
        viewModel.init(album)
        observeState()
    }

    private fun observeState() {
        stateDisposable = viewModel.state
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { state -> onStateChanged(state) }
    }

    private fun onStateChanged(state: AlbumState) {
        toolbarController.setTitle(state.title)
        album_author.text = state.author
        album_upvotes.text = state.upvotes.toString()
        album_downvotes.text = state.downvotes.toString()
        album_views.text = state.views.toString()
        album_favorites.text = state.favorites.toString()
        // TODO: load images
    }
}
