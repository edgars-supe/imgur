package lv.esupe.imgur.master

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import lv.esupe.imgur.Navigator
import lv.esupe.imgur.R
import lv.esupe.imgur.ViewModelFactory

class MasterFragment : Fragment() {

    companion object {
        fun newInstance() = MasterFragment()
    }

    private val viewModel: MasterViewModel by viewModels {
        val navigator = activity as? Navigator ?: error("Hosting Activity must implement Navigator")
        ViewModelFactory(navigator)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.main_fragment, container, false)


}
