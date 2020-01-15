package lv.esupe.imgur

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView


class AspectRatioImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {
    var aspectRatio: Float = 1.0f

    fun setAspectRatioFromDimensions(width: Int, height: Int) {
        aspectRatio = height.toFloat() / width
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val heightSpec =
            MeasureSpec.makeMeasureSpec((width * aspectRatio).toInt(), MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, heightSpec)
    }
}
