package com.sun.qakhadelivery.screens.orderdetail.adapter

import android.view.ViewGroup
import com.sun.qakhadelivery.data.model.Cart
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import com.sun.qakhadelivery.widget.recyclerview.item.BucketItem
import com.sun.qakhadelivery.widget.recyclerview.item.CartItem
import com.sun.qakhadelivery.widget.recyclerview.viewholder.BucketViewHolder

class BucketAdapter : CustomRecyclerView.Adapter<BucketViewHolder>(arrayListOf()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomRecyclerView.ViewHolder<*> {
        return BucketViewHolder(parent)
    }

    fun updateData(carts: MutableList<Cart>) {
        mItems.clear()
        addItems(carts.map {
            BucketItem(it)
        })
    }

    fun geSubtotalPrice(): Float {
        return getItems<CartItem>()
            .map { it.cart.quantity * it.cart.product.price }
            .sum()
    }
}
