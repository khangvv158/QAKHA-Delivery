package com.sun.qakhadelivery.screens.cart.adapter

import android.view.ViewGroup
import com.sun.qakhadelivery.data.model.Cart
import com.sun.qakhadelivery.data.source.remote.schema.request.CartRequest
import com.sun.qakhadelivery.data.source.remote.schema.request.RemoveCartRequest
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import com.sun.qakhadelivery.widget.recyclerview.item.CartItem
import com.sun.qakhadelivery.widget.recyclerview.viewholder.CartViewHolder

class CartAdapter : CustomRecyclerView.Adapter<CartViewHolder>(arrayListOf()),
    CartViewHolder.OnClickCartViewHolderListener {

    private var listener: OnClickCartListener.CartListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomRecyclerView.ViewHolder<*> {
        return CartViewHolder(parent).apply {
            setOnClickListener(getItems<CartItem>().toMutableList(), this@CartAdapter)
        }
    }

    override fun increase(cartRequest: CartRequest) {
        listener?.increase(cartRequest)
    }

    override fun decrease(cartRequest: CartRequest) {
        listener?.decrease(cartRequest)
    }

    override fun remove(removeCartRequest: RemoveCartRequest) {
        listener?.remove(removeCartRequest)
    }

    fun updateProducts(carts: MutableList<Cart>) {
        clearItems()
        addItems(carts.map {
            CartItem(it)
        })
    }

    fun clearProducts() {
        clearItems()
        if (mItems.isEmpty()) listener?.finish()
    }

    fun setOnClickCartListener(listener: OnClickCartListener.CartListener) {
        this.listener = listener
    }

    fun getCarts(): MutableList<Cart> {
        return getItems<CartItem>().map { it.cart }.toMutableList()
    }

    interface OnClickCartListener {

        interface CartListener {

            fun increase(cartRequest: CartRequest)

            fun decrease(cartRequest: CartRequest)

            fun remove(removeCartRequest: RemoveCartRequest)

            fun finish()
        }
    }
}
