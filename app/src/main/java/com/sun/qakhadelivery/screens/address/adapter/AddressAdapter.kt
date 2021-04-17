package com.sun.qakhadelivery.screens.address.adapter

import android.view.ViewGroup
import com.sun.qakhadelivery.data.model.Address
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import com.sun.qakhadelivery.widget.recyclerview.item.AddressItem
import com.sun.qakhadelivery.widget.recyclerview.viewholder.AddressViewHolder

class AddressAdapter : CustomRecyclerView.Adapter<AddressViewHolder>(arrayListOf()) {

    private var listener: AddressAdapterOnClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomRecyclerView.ViewHolder<*> {
        return AddressViewHolder(parent).apply {
            registerOnClickItem {
                getItemPosition<AddressItem>(adapterPosition)?.address?.let { address ->
                    listener?.onItemClickListener(address)
                }
            }
            registerOnLongClickItem {
                getItemPosition<AddressItem>(adapterPosition)?.address?.let { address ->
                    listener?.onItemLongClickListener(address)
                }
            }
        }
    }

    fun updateData(addresses: MutableList<Address>) {
        clearItems()
        addItems(addresses.map {
            AddressItem(it)
        })
        notifyDataSetChanged()
    }

    fun registerListener(listener: AddressAdapterOnClickListener) {
        this.listener = listener
    }
}
