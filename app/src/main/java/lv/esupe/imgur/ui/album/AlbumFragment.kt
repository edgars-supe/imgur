package lv.esupe.imgur.ui.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import lv.esupe.imgur.R
import lv.esupe.imgur.utils.component
import lv.esupe.imgur.utils.viewModel

class AlbumFragment : Fragment() {

    companion object {
        fun newInstance() = AlbumFragment()
    }

    private val viewModel by viewModel { component().albumViewModel }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_master, container, false)

}
