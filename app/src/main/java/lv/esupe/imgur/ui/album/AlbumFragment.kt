package lv.esupe.imgur.ui.album

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import kotlinx.android.synthetic.main.fragment_album.*
import lv.esupe.imgur.Navigator
import lv.esupe.imgur.R
import lv.esupe.imgur.model.ImgurItem
import lv.esupe.imgur.ui.album.recycler.AlbumImageAdapter
import lv.esupe.imgur.ui.album.recycler.AlbumImageDiffCallback
import lv.esupe.imgur.ui.album.recycler.SpacingItemDecoration
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
    private var eventDisposable: Disposable = Disposables.disposed()
    private val adapter = AlbumImageAdapter(AlbumImageDiffCallback(), ::onItemClicked)

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
        val spacing = resources.getDimensionPixelSize(R.dimen.spacing_mini)
        album_images.layoutManager =
            GridLayoutManager(view.context, getSpanCount(), GridLayoutManager.VERTICAL, false)
        album_images.adapter = adapter
        val album = arguments?.getParcelable<ImgurItem.Album>(EXTRA_ALBUM)
            ?: error("Couldn't read album")
        album_images.addItemDecoration(SpacingItemDecoration(spacing))
        viewModel.init(album)
        observeState()
        observeEvents()
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
        adapter.submitList(state.images)
    }

    private fun observeEvents() {
        eventDisposable = viewModel.events
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event -> onEventReceived(event) }
    }

    private fun onEventReceived(event: AlbumEvent) {
        when (event) {
            is AlbumEvent.ShowImage -> (activity as? Navigator)?.showImage(event.image)
        }
    }

    private fun onItemClicked(position: Int) {
        viewModel.onItemClicked(position)
    }

    private fun getSpanCount(): Int {
        val itemWidth = resources.getDimensionPixelSize(R.dimen.album_image_size)
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels / itemWidth
    }
}
