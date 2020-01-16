package lv.esupe.imgur.ui.album.recycler

import androidx.recyclerview.widget.DiffUtil
import lv.esupe.imgur.ui.album.model.AlbumImage

class AlbumImageDiffCallback : DiffUtil.ItemCallback<AlbumImage>() {
    override fun areItemsTheSame(oldItem: AlbumImage, newItem: AlbumImage): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: AlbumImage, newItem: AlbumImage): Boolean =
        oldItem == newItem
}
