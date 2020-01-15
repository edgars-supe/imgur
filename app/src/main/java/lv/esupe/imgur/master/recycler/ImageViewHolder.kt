package lv.esupe.imgur.master.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_image.view.*
import lv.esupe.imgur.master.model.ImageItem

class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: ImageItem) {
        setTitle(item.title)
        loadImage(item.link, item.width, item.height)
    }

    private fun loadImage(link: String, width: Int, height: Int) {
        // changing the aspect ratio ensures that the image fills the width of the ImageView
        // and the ImageView's height is matched appropriately before the image is loaded (luckily,
        // the Imgur API provides us with this info)
        itemView.image_container.setAspectRatioFromDimensions(width, height)
        Glide.with(itemView)
            .load(link)
            .into(itemView.image_container)
    }

    private fun setTitle(title: String) {
        itemView.image_title.text = title
    }
}
