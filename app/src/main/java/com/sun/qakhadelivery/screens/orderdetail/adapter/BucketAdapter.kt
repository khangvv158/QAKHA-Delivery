package com.sun.qakhadelivery.screens.orderdetail.adapter

import android.view.ViewGroup
import com.sun.qakhadelivery.data.model.Cart
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import com.sun.qakhadelivery.widget.recyclerview.item.BucketItem
import com.sun.qakhadelivery.widget.recyclerview.viewholder.BucketViewHolder

class BucketAdapter : CustomRecyclerView.Adapter<BucketViewHolder>(arrayListOf()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomRecyclerView.ViewHolder<*> {
        return BucketViewHolder(parent)
    }

    fun updateData(carts: MutableList<Cart>) {
        mItems.clear()
        addItems(carts.map {
            BucketItem(it)
        })
    }

    fun geSubtotalPrice(): Float {
        var subTotal = 0f
        getItems<BucketItem>().forEach {
            subTotal += it.cart.product.price * it.cart.amount
        }
        return subTotal
    }
}
