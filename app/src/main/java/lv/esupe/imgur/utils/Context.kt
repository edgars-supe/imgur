package lv.esupe.imgur.utils

import androidx.fragment.app.Fragment
import lv.esupe.imgur.ImgurApp

fun Fragment.component() = (requireContext().applicationContext as ImgurApp).component
