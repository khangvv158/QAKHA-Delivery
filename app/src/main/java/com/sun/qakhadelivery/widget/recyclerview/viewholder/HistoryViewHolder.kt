package com.sun.qakhadelivery.widget.recyclerview.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.source.remote.schema.response.HistoryResponse
import com.sun.qakhadelivery.extensions.setOnSafeClickListener
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import com.sun.qakhadelivery.widget.recyclerview.item.HistoryItem
import kotlinx.android.synthetic.main.item_layout_order.view.*

class HistoryViewHolder(viewGroup: ViewGroup) :
    CustomRecyclerView.ViewHolder<HistoryItem>(newInstance(viewGroup)) {

    override fun bind(item: HistoryItem) {
        with(itemView) {
            textViewIdOrder.text = item.history.id.toString()
            namePartnerHighTextView.text = item.history.partner.name
            textViewAddressPartner.text = item.history.partner.address
            textViewPrice.text = item.history.total.toString()
            textViewStatus.text = item.history.status
        }
    }

    fun registerOnClickItem(
        items: MutableList<HistoryItem>,
        listener: (HistoryResponse) -> Unit
    ) {
        itemView.setOnSafeClickListener(2000) {
            listener(items[adapterPosition].history)
        }
    }

    companion object {
        fun newInstance(viewGroup: ViewGroup): View {
            return LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_layout_order, viewGroup, false)
        }
    }
}
