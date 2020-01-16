package lv.esupe.imgur.ui.master.recycler

import androidx.recyclerview.widget.DiffUtil
import lv.esupe.imgur.ui.master.model.ImgurListItem

class ItemDiffCallback : DiffUtil.ItemCallback<ImgurListItem>() {
    override fun areItemsTheSame(oldItem: ImgurListItem, newItem: ImgurListItem): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ImgurListItem, newItem: ImgurListItem): Boolean =
        oldItem.link == newItem.link
}
