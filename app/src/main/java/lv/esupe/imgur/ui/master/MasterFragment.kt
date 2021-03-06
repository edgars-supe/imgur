package lv.esupe.imgur.ui.master

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import kotlinx.android.synthetic.main.fragment_master.*
import kotlinx.android.synthetic.main.view_error.*
import lv.esupe.imgur.Navigator
import lv.esupe.imgur.R
import lv.esupe.imgur.ui.master.recycler.ItemAdapter
import lv.esupe.imgur.ui.master.recycler.ItemDiffCallback
import lv.esupe.imgur.utils.component
import lv.esupe.imgur.utils.toolbarController
import lv.esupe.imgur.utils.viewModel

class MasterFragment : Fragment() {

    companion object {
        fun newInstance() = MasterFragment()
    }

    private val viewModel by viewModel { component().masterViewModel }
    private val adapter = ItemAdapter(ItemDiffCallback(), ::onItemClicked)
    private var stateDisposable = Disposables.disposed()
    private var eventDisposable = Disposables.disposed()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_master, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbarController.disableBackButton()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
        observeEvents()
        master_recycler.adapter = adapter
        master_recycler.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stateDisposable.dispose()
        eventDisposable.dispose()
    }

    private fun onItemClicked(position: Int) {
        viewModel.onItemClicked(position)
    }

    private fun observeState() {
        stateDisposable = viewModel.state
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { state -> onStateChanged(state) }
    }

    private fun onStateChanged(state: MasterState) {
        toggleStateViews(state)
        when (state) {
            is MasterState.Loading -> onLoading()
            is MasterState.Content -> onContent(state)
            is MasterState.Error -> onError(state)
        }
    }

    private fun toggleStateViews(state: MasterState) {
        master_progress_bar.isVisible = state is MasterState.Loading
        master_recycler.isVisible = state is MasterState.Content
        error_root.isVisible = state is MasterState.Error
    }

    private fun onLoading() {
    }

    private fun onContent(state: MasterState.Content) {
        toolbarController.setTitle(state.title)
        adapter.submitList(state.images)
    }

    private fun onError(state: MasterState.Error) {
        error_text.text = state.message
        error_try_again.setOnClickListener { viewModel.onTryAgainClicked() }
    }

    private fun observeEvents() {
        eventDisposable = viewModel.events
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event -> onEventReceived(event) }
    }

    private fun onEventReceived(event: MasterEvent) {
        when (event) {
            is MasterEvent.ShowImage -> (activity as? Navigator)?.showImage(event.image)
            is MasterEvent.ShowAlbum -> (activity as? Navigator)?.showAlbum(event.album)
        }
    }
}
