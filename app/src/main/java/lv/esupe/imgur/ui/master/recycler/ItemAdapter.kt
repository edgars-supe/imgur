package lv.esupe.imgur.ui.master.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import lv.esupe.imgur.R
import lv.esupe.imgur.ui.master.model.ImgurListItem

class ItemAdapter(
    callback: ItemDiffCallback,
    var onItemClicked: ((position: Int) -> Unit)? = null
) : ListAdapter<ImgurListItem, ItemViewHolder>(callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_imgur, parent, false)
        val vh = ItemViewHolder(layout)
        vh.itemView.setOnClickListener {
            if (vh.adapterPosition != RecyclerView.NO_POSITION) {
                onItemClicked?.invoke(vh.adapterPosition)
            }
        }
        return vh
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
