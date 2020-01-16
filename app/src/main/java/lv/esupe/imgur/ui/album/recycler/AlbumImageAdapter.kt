package lv.esupe.imgur.ui.album.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import lv.esupe.imgur.R
import lv.esupe.imgur.ui.album.model.AlbumImage

class AlbumImageAdapter(
    callback: DiffUtil.ItemCallback<AlbumImage>,
    var onItemClicked: ((position: Int) -> Unit)? = null
) : ListAdapter<AlbumImage, AlbumImageViewHolder>(callback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_album_image, parent, false)
        val vh = AlbumImageViewHolder(view)
        vh.itemView.setOnClickListener {
            if (vh.adapterPosition != RecyclerView.NO_POSITION) {
                onItemClicked?.invoke(vh.adapterPosition)
            }
        }
        return vh
    }

    override fun onBindViewHolder(holder: AlbumImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
