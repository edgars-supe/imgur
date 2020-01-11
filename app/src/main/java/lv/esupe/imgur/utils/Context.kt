package lv.esupe.imgur.utils

import android.app.Activity
import androidx.fragment.app.Fragment
import lv.esupe.imgur.ImgurApp

fun Activity.component() = (application as ImgurApp).component

fun Fragment.component() = (requireContext().applicationContext as ImgurApp).component
