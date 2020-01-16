package lv.esupe.imgur.ui.image

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import kotlinx.android.synthetic.main.fragment_image.*
import lv.esupe.imgur.R
import lv.esupe.imgur.model.ImgurItem
import lv.esupe.imgur.utils.component
import lv.esupe.imgur.utils.viewModel

class ImageFragment : BottomSheetDialogFragment() {

    companion object {
        private const val EXTRA_IMAGE = "IMAGE"

        fun newInstance(image: ImgurItem.Image) = ImageFragment().apply {
            arguments = bundleOf(EXTRA_IMAGE to image)
        }
    }

    private val viewModel by viewModel { component().imageViewModel }
    private var stateDisposable: Disposable = Disposables.disposed()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_image, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpBehavior()

        val image = arguments?.getParcelable<ImgurItem.Image>(EXTRA_IMAGE)
            ?: error("Couldn't retrieve image")
        viewModel.init(image)
        observeState()
    }

    private fun setUpBehavior() {
        dialog?.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet =
                d.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            bottomSheetBehavior.skipCollapsed = true
        }
    }

    private fun observeState() {
        stateDisposable = viewModel.state
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { state -> onStateChanged(state) }
    }

    private fun onStateChanged(state: ImageState) {
        image_title.text = state.title
        image_description.isVisible = !state.description.isNullOrBlank()
        image_description.text = state.description

        Glide.with(requireContext())
            .load(state.link)
            .into(image_container)
    }
}
