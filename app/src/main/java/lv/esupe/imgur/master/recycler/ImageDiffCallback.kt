package lv.esupe.imgur.master.recycler

import androidx.recyclerview.widget.DiffUtil
import lv.esupe.imgur.master.model.ImageItem

class ImageDiffCallback : DiffUtil.ItemCallback<ImageItem>() {
    override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean =
        oldItem.link == newItem.link
}
