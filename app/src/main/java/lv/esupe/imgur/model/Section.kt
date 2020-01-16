package lv.esupe.imgur.model

import androidx.annotation.StringRes
import lv.esupe.imgur.R

enum class Section(val id: String, @StringRes val resId: Int) {
    Hot("hot", R.string.section_hot)
}
