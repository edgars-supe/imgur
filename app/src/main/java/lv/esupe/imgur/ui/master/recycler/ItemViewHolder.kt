package lv.esupe.imgur.ui.master.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_imgur.view.*
import lv.esupe.imgur.R
import lv.esupe.imgur.ui.master.model.ImgurListItem

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: ImgurListItem) {
        setTitle(item.title)
        loadImage(item.link)
        setUpIcon(item.isAlbum, item.albumSize)
    }

    private fun loadImage(link: String) {
        Glide.with(itemView)
            .load(link)
            .into(itemView.image_container)
    }

    private fun setTitle(title: String) {
        itemView.image_title.text = title
    }

    private fun setUpIcon(isAlbum: Boolean, albumSize: Int) {
        val icon = if (isAlbum) R.drawable.ic_album else R.drawable.ic_image
        itemView.item_icon.setImageResource(icon)
        itemView.item_album_size.text = if (isAlbum) albumSize.toString() else ""
    }
}
