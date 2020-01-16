package lv.esupe.imgur.ui.album.recycler

import android.graphics.Rect
import android.view.View
import androidx.annotation.Dimension
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * This item decoration ensures even distribution of spacing in a grid of items. For example,
 * the top row will have full spacing on top (as specified by [spacing]), but items in the rows
 * below will only have half the spacing added, because the bottom spacing from the above items will
 * serve as the other half of the top spacing.
 */
class SpacingItemDecoration(@Dimension var spacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val lm = parent.layoutManager

        if (lm !is GridLayoutManager) {
            outRect.setEmpty()
            return
        }
        val idx = lm.getPosition(view)
        if (idx < 0) {
            outRect.setEmpty()
            return
        }

        val half = spacing / 2
        val spanCount = lm.spanCount
        val isLeft = idx % spanCount == 0
        val isTop = idx < spanCount
        val isRight = idx % spanCount == spanCount - 1
        val isBottom = idx > lm.itemCount - spanCount
        outRect.set(
            if (isLeft) spacing else half,
            if (isTop) spacing else half,
            if (isRight) spacing else half,
            if (isBottom) spacing else half
        )
    }
}
