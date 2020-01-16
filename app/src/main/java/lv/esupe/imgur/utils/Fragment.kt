package lv.esupe.imgur.utils

import androidx.fragment.app.Fragment
import lv.esupe.imgur.ToolbarController

val Fragment.toolbarController: ToolbarController
    get() = activity as ToolbarController
