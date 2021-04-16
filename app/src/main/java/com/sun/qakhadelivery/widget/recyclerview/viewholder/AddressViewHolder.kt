package com.sun.qakhadelivery.widget.recyclerview.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import com.sun.qakhadelivery.widget.recyclerview.item.AddressItem
import kotlinx.android.synthetic.main.item_layout_address.view.*

class AddressViewHolder(viewGroup: ViewGroup) :
    CustomRecyclerView.ViewHolder<AddressItem>(newInstance(viewGroup)) {

    override fun bind(item: AddressItem) {
        with(itemView) {
            textViewAddress.text = item.address.name
        }
    }

    fun registerOnClickItem(listener: (View) -> Unit) {
        itemView.setOnClickListener {
            listener(it)
        }
    }

    fun registerOnLongClickItem(listener: (View) -> Unit) {
        itemView.setOnLongClickListener {
            listener(it)
            true
        }
    }

    companion object {

        fun newInstance(viewGroup: ViewGroup): View {
            return LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_layout_address, viewGroup, false)
        }
    }
}