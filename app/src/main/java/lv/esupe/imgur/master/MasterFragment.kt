package lv.esupe.imgur.master

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_master.*
import kotlinx.android.synthetic.main.view_error.*
import lv.esupe.imgur.R
import lv.esupe.imgur.master.recycler.ImageAdapter
import lv.esupe.imgur.master.recycler.ImageDiffCallback
import lv.esupe.imgur.utils.component
import lv.esupe.imgur.utils.viewModel

class MasterFragment : Fragment() {

    companion object {
        fun newInstance() = MasterFragment()
    }

    private val viewModel by viewModel { component().masterViewModel }
    private val adapter = ImageAdapter(ImageDiffCallback(), ::onImageClicked)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_master, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(this, getStateChangeObserver())
        master_recycler.adapter = adapter
        master_recycler.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
    }

    private fun getStateChangeObserver(): Observer<MasterState> = Observer { state ->
        toggleStateViews(state)
        when (state) {
            is MasterState.Loading -> onLoading()
            is MasterState.Content -> onContent(state)
            is MasterState.Error -> onError(state)
        }
    }

    private fun onLoading() {
    }

    private fun onContent(state: MasterState.Content) {
        adapter.submitList(state.images)
    }

    private fun onError(state: MasterState.Error) {
        if (view?.findViewById<View>(R.id.master_error_stub) != null) {
            master_error_stub.inflate()
        }
        error_text.text = state.message
    }

    private fun toggleStateViews(state: MasterState) {
        master_progress_bar.isVisible = state is MasterState.Loading
        master_recycler.isVisible = state is MasterState.Content
        master_error_stub.isVisible = state is MasterState.Error
    }

    private fun onImageClicked(position: Int) {
        viewModel.onImageClicked(position)
    }
}
