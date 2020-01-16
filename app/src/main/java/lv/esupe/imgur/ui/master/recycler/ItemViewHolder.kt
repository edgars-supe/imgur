package lv.esupe.imgur.ui.master.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_imgur.view.*
import lv.esupe.imgur.ui.master.model.ImgurListItem

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: ImgurListItem) {
        setTitle(item.title)
        loadImage(item.link)
    }

    private fun loadImage(link: String) {
        Glide.with(itemView)
            .load(link)
            .into(itemView.image_container)
    }

    private fun setTitle(title: String) {
        itemView.image_title.text = title
    }
}
