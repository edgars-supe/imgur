package lv.esupe.imgur.ui.album.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_album_image.view.*
import lv.esupe.imgur.ui.album.model.AlbumImage

class AlbumImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(albumImage: AlbumImage) {
        Glide.with(itemView)
            .load(albumImage.link)
            .into(itemView.album_image_container)
    }
}
