package lv.esupe.imgur.ui.image

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import lv.esupe.imgur.R
import lv.esupe.imgur.ui.album.AlbumFragment
import lv.esupe.imgur.utils.component
import lv.esupe.imgur.utils.viewModel

class ImageFragment : Fragment() {

    companion object {
        fun newInstance() = AlbumFragment()
    }

    private val viewModel by viewModel { component().imageViewModel }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_master, container, false)

}
