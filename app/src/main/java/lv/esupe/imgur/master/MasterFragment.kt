package lv.esupe.imgur.master

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import lv.esupe.imgur.R
import lv.esupe.imgur.utils.component
import lv.esupe.imgur.utils.viewModel

class MasterFragment : Fragment() {

    companion object {
        fun newInstance() = MasterFragment()
    }

    private val viewModel by viewModel { component().masterViewModel }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_master, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(this, getStateChangeObserver())
    }

    private fun getStateChangeObserver(): Observer<MasterState> = Observer { state ->
        when (state) {
            is MasterState.Loading -> onLoading()
            is MasterState.Content -> onContent(state)
            is MasterState.Error -> onError(state)
        }
    }

    private fun onLoading() {

    }

    private fun onContent(state: MasterState.Content) {

    }

    private fun onError(state: MasterState.Error) {
        println(state.message)
    }
}
