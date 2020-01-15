package lv.esupe.imgur.master.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import lv.esupe.imgur.R
import lv.esupe.imgur.master.model.ImageItem

class ImageAdapter(
    callback: ImageDiffCallback,
    var onItemClicked: ((position: Int) -> Unit)? = null
) : ListAdapter<ImageItem, ImageViewHolder>(callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false)
        val vh = ImageViewHolder(layout)
        vh.itemView.setOnClickListener {
            if (vh.adapterPosition != RecyclerView.NO_POSITION) {
                onItemClicked?.invoke(vh.adapterPosition)
            }
        }
        return vh
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
