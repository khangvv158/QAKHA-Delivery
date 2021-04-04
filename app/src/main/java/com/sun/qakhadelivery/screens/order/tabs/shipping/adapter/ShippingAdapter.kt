package com.sun.qakhadelivery.screens.order.tabs.shipping.adapter

import android.util.Log
import android.view.ViewGroup
import com.sun.qakhadelivery.data.model.Event
import com.sun.qakhadelivery.data.model.Order
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import com.sun.qakhadelivery.widget.recyclerview.item.ShippingItem
import com.sun.qakhadelivery.widget.recyclerview.viewholder.ShippingViewHolder
import org.greenrobot.eventbus.EventBus

class ShippingAdapter : CustomRecyclerView.Adapter<ShippingViewHolder>(arrayListOf()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomRecyclerView.ViewHolder<*> {
        return ShippingViewHolder(parent).apply {
            setOnClickItem {
                EventBus.getDefault().post(
                        Event(EVENT_SHIPPING, getItemPosition<ShippingItem>(adapterPosition)?.order)
                )
            }
        }
    }

    fun updateData(orders: MutableList<Order>) {
        mItems.clear()
        addItems(orders.map {
            Log.e("data", it.id.toString())
            ShippingItem(it)
        })
        notifyDataSetChanged()
    }

    companion object {
        const val EVENT_SHIPPING = "EVENT_SHIPPING"
    }
}
