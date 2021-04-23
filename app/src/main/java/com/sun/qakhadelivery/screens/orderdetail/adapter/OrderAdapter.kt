package com.sun.qakhadelivery.screens.orderdetail.adapter

import android.view.ViewGroup
import com.sun.qakhadelivery.data.model.OrderDetails
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import com.sun.qakhadelivery.widget.recyclerview.item.OrderItem
import com.sun.qakhadelivery.widget.recyclerview.viewholder.OrderViewHolder

class OrderAdapter : CustomRecyclerView.Adapter<OrderViewHolder>(arrayListOf()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomRecyclerView.ViewHolder<*> {
        return OrderViewHolder(parent)
    }

    fun updateOrderDetails(orderDetails: MutableList<OrderDetails>) {
        updateItems(orderDetails.map {
            OrderItem(it)
        })
    }
}
