package com.sun.qakhadelivery.widget.recyclerview.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import com.sun.qakhadelivery.widget.recyclerview.item.ShippingItem

class ShippingViewHolder(viewGroup: ViewGroup)
    : CustomRecyclerView.ViewHolder<ShippingItem>(newInstance(viewGroup)) {

    override fun bind(item: ShippingItem) {
    }

    fun setOnClickItem(listener: (View) -> Unit) {
        itemView.setOnClickListener {
            listener(itemView)
        }
    }

    companion object {
        fun newInstance(viewGroup: ViewGroup): View {
            return LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.item_layout_order, viewGroup, false)
        }
    }
}
