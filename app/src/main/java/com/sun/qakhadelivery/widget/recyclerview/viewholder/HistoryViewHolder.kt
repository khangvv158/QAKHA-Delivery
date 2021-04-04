package com.sun.qakhadelivery.widget.recyclerview.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import com.sun.qakhadelivery.widget.recyclerview.item.HistoryItem
import kotlinx.android.synthetic.main.item_layout_order.view.*

class HistoryViewHolder(viewGroup: ViewGroup)
    : CustomRecyclerView.ViewHolder<HistoryItem>(newInstance(viewGroup)) {

    override fun bind(item: HistoryItem) {
        with(itemView) {
            textViewIdOrder?.text = item.order.id.toString()
            textViewDate?.text = item.order.date
            textViewNamePartner?.text = item.order.partner?.name
            textViewAddressPartner?.text = item.order.partner?.address
            textViewPrice?.text = item.order.total.toString()
            textViewStatus.text = context.getString(R.string.complete)
        }
    }

    fun registerOnClickItem(listener: (View) -> Unit) {
        listener(itemView)
    }

    companion object {
        fun newInstance(viewGroup: ViewGroup): View {
            return LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.item_layout_order, viewGroup, false)
        }
    }
}
